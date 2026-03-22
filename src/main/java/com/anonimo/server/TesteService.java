package com.anonimo.server;

import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TesteService {

	@Inject
	TesteRepository testeRepository;

	@LogExecution
	public List<Map<String, Object>>  conexaoBanco() {
		List<Map<String, Object>> a = testeRepository.retornoBanco();
		if (a != null)
			return a;

		return null;
	}
	
    @LogExecution
    public void processarPedido(String teste) {
        System.out.println("Executando lógica...");
    }
	
}
