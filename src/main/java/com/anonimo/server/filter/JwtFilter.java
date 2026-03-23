package com.anonimo.server.filter;

import java.security.Principal;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {
	
    @Inject
    JwtService jwtService;	
	
	@Override
    public void filter(ContainerRequestContext requestContext) {

		String authHeader = requestContext.getHeaderString("Authorization");
        String token = jwtService.extrairToken(authHeader);

        var claims = jwtService.validarToken(token);

        if (claims == null) {
            requestContext.abortWith(Response.status(401).build());
            return;
        }
        
        String usuario = claims.getSubject();

        SecurityContext original = requestContext.getSecurityContext();

        requestContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {
                return () -> usuario;
            }

            @Override
            public boolean isUserInRole(String role) {
                return role.equals(claims.get("role"));
            }

            @Override
            public boolean isSecure() {
                return original.isSecure();
            }

            @Override
            public String getAuthenticationScheme() {
                return "Bearer";
            }
        });
    }
}
