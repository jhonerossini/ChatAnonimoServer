package com.anonimo.server.resource;

import java.util.List;
import java.util.stream.Collectors;

import com.anonimo.server.dto.UsuarioOnlineDTO;
import com.anonimo.server.entity.UsuarioEntity;
import com.anonimo.server.repository.UsuarioRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/chat/sala")
@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class SalaResource {

	@Inject
    UsuarioRepository usuarioRepository;

    @GET
    @Path("/online")
    public List<UsuarioOnlineDTO> listarOnline() {
        
        // 1. Busca as entidades no banco
        List<UsuarioEntity> logados = usuarioRepository.listarUsuariosOnline();

        // 2. Converte a lista de Entidades para a lista de DTOs seguros
        return logados.stream()
                .map(usuario -> new UsuarioOnlineDTO(
                		usuario.id,
                        usuario.nick, 
                        usuario.nome, 
                        usuario.statusGlobal
                ))
                .collect(Collectors.toList());
    }
    
}
