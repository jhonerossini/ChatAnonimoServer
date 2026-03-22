package com.anonimo.server;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/auth")
public class JwtResource {
	
	@Inject
    JwtService jwtService;

    @GET
    @Path("/token")
    public String gerar() {
        return jwtService.gerarToken("jhone");
    }
}
