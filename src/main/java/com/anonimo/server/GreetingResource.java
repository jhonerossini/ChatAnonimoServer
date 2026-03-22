package com.anonimo.server;

import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class GreetingResource {

	@Inject
	TesteService testeService;
	
    @GET
    @Path("anotation/{teste}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(@PathParam("teste") String teste) {
    	
    	testeService.processarPedido(teste);
    	
        return Response.ok().build();
    }
    
    @GET
    @Path("banco")
    @Produces(MediaType.TEXT_PLAIN)
    public Response conexaoBanco() {
    	
    	List<Map<String, Object>> a = testeService.conexaoBanco();
    	
    	if(a != null)
    		return Response.ok(a).build();
    	
        return Response.noContent().build();
    }
}
