package com.anonimo.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class TesteRepository {
	
	@Inject
	EntityManager em;
	
	public List<Map<String, Object>> retornoBanco() {

	    String sql = """
	            select email, senha, nome, sobrenome from login
	            """;

	    List<Object[]> result = em.createNativeQuery(sql).getResultList();
	    List<Map<String, Object>> lista = new ArrayList<>();

	    for (Object[] row : result) {
	        Map<String, Object> map = new HashMap<>();
	        map.put("email", row[0]);
	        map.put("senha", row[1]);
	        map.put("nome", row[2]);
	        map.put("sobrenome", row[3]);
	        lista.add(map);
	    }

	    return lista;
	}

}
