package com.anonimo.server.resource;

import java.util.Map;

import com.anonimo.server.dto.LoginRequestDTO;
import com.anonimo.server.filter.Secured;
import com.anonimo.server.service.LoginService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Path("/chat")
public class LoginResource {
	
	@Inject
	LoginService loginService;
	
	@POST
    @Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logar(LoginRequestDTO login){
    	
    	Map<String, Object> token = loginService.logar(login);
		
    	if(token == null)
    		return Response.status(Status.UNAUTHORIZED).entity("Usuário ou senha incorreto.").build();
    	
        return Response.ok(token).build();
    }
	
	@Secured
	@POST
    @Path("/logout")
    public Response deslogar(@Context SecurityContext securityContext) {
		
        String loginDoUsuario = securityContext.getUserPrincipal().getName();
        
        boolean deslogado = loginService.deslogar(loginDoUsuario);
        
        if (!deslogado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"erro\": \"Usuário não encontrado\"}")
                    .build();
        }

        return Response.ok("{\"mensagem\": \"Logout realizado. Usuário saiu da sala.\"}").build();
    }
}
