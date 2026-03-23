package com.anonimo.server.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Override
    public void filter(ContainerRequestContext requestContext) {
        // 1. O SINAL VERDE PARA O PREFLIGHT:
        // Se o navegador enviar um OPTIONS perguntando se pode passar, 
        // abortamos a fila de filtros aqui mesmo e dizemos "Pode passar! (200 OK)"
        if (requestContext.getMethod().equalsIgnoreCase("OPTIONS")) {
            requestContext.abortWith(Response.ok().build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        // 2. O CARIMBO DE PERMISSÃO EM TODAS AS RESPOSTAS:
        // Seja um 200 (Sucesso), 401 (Erro) ou o 200 do OPTIONS ali de cima, 
        // nós grudamos esses cabeçalhos para o React (Navegador) ficar feliz.
        responseContext.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        responseContext.getHeaders().putSingle("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
    }
}
