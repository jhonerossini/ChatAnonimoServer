package com.anonimo.server.resource;

import com.anonimo.server.dto.UsuarioRequestDTO;
import com.anonimo.server.entity.UsuarioEntity;
import com.anonimo.server.service.UsuarioService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chat/usuario")
public class UsuarioResource {
	
	@Inject
	UsuarioService usuarioService;
	
	@POST
    @Path("/criar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response criarUsuario(UsuarioRequestDTO request) {
        
		UsuarioEntity retorno = usuarioService.criarUsuario(request);

        if (retorno == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"erro\": \"Login ou senha inválidos\"}")
                    .build();
        }

        // 5. Retornar o token com status 200 OK
        return Response.ok(retorno).build();
    }
}
