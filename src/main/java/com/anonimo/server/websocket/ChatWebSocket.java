package com.anonimo.server.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import com.anonimo.server.filter.JwtService;
import com.anonimo.server.service.ChatUsuarioService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{token}")
@ApplicationScoped
public class ChatWebSocket {

    @Inject
    JwtService jwtService;

    @Inject
    ChatUsuarioService chatUsuarioService; // Nosso novo serviço transacional

    // Guarda a Sessão ligada ao Login
    private final Map<Session, String> sessoesAtivas = new ConcurrentHashMap<>();
    
    // Semáforo intrinsecamente ligado ao Login (Garante 1 ação por vez por usuário)
    private final Map<String, Semaphore> semaforosPorUsuario = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        var claims = jwtService.validarToken(token);
        if (claims == null) {
            fecharSessaoComErro(session, "Token inválido ou expirado");
            return;
        }

        String login = claims.getSubject();
        sessoesAtivas.put(session, login);
        
        // Se o usuário não tem um semáforo ainda, cria um com 1 permissão (Mutex)
        semaforosPorUsuario.putIfAbsent(login, new Semaphore(1));

        // Dispara a Virtual Thread do JDK 21
        Thread.startVirtualThread(() -> {
            Semaphore semaforo = semaforosPorUsuario.get(login);
            try {
                semaforo.acquire(); // Trava: Espera se já houver outra ação deste usuário rodando
                chatUsuarioService.registrarEntrada(login); // Chama o método @Transactional
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrompida para o usuário: " + login);
            } finally {
                semaforo.release(); // Libera o semáforo para a próxima ação
            }
        });
    }

    @OnClose
    public void onClose(Session session) {
        String login = sessoesAtivas.remove(session);

        if (login != null) {
            Thread.startVirtualThread(() -> {
                Semaphore semaforo = semaforosPorUsuario.get(login);
                if (semaforo == null) return;

                try {
                    semaforo.acquire();
                    chatUsuarioService.registrarSaida(login);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaforo.release();
                    // Limpa o semáforo da memória se a sessão foi totalmente encerrada
                    if (!sessoesAtivas.containsValue(login)) {
                        semaforosPorUsuario.remove(login);
                    }
                }
            });
        }
    }

    @OnError
    public void onError(Session session, Throwable erro) {
        System.err.println("Erro na conexão WebSocket: " + erro.getMessage());
        onClose(session);
    }

    @OnMessage
    public void onMessage(String mensagem, Session session) {
        String remetente = sessoesAtivas.get(session);
        if (remetente == null) return;

        // Se quiser processar mensagens no banco (salvar histórico), 
        // faça o mesmo padrão de Thread.startVirtualThread + semaforo.acquire() aqui!
        System.out.println("Mensagem recebida de " + remetente + ": " + mensagem);
    }

    private void fecharSessaoComErro(Session session, String motivo) {
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, motivo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}