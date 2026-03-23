package com.anonimo.server.resource;

import java.util.HashMap;
import java.util.Map;

import com.anonimo.server.dto.LoginRequestDTO;
import com.anonimo.server.entity.LoginEntity;
import com.anonimo.server.filter.JwtService;
import com.anonimo.server.filter.Secured;
import com.anonimo.server.repository.LoginRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

@Path("/chat/auth")
public class LoginResource {
	
	@Inject
    JwtService jwtService;
	
	@Inject
	LoginRepository loginRepository;

	@POST
    @Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logar(LoginRequestDTO login){
    	//1. Buscar o usuário pelo login informado
    	LoginEntity usuario = loginRepository.findByLogin(login.getLogin());
    	
    	if(usuario == null || !BcryptUtil.matches(login.getSenha(), usuario.senha))
    		return Response.status(Status.UNAUTHORIZED).entity("Usuário ou senha incorreto.").build();
    	
    	// 2. Gerar o token JWT
        String token = jwtService.gerarToken(usuario.login);
        
        Map<String, Object> retorno = new HashMap<>();
        retorno.put("token", token);
        return Response.ok(retorno).build();
    }
    
    @Secured
    @GET
    public Response acessar(@Context SecurityContext securityContext) {
        return Response.ok("Bem-vindo " + securityContext.getUserPrincipal().getName()).build();
    }
}
