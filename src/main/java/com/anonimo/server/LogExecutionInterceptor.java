package com.anonimo.server;

import jakarta.interceptor.*;

import java.util.Arrays;

import jakarta.annotation.Priority;

@LogExecution
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class LogExecutionInterceptor {

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        System.out.println("Iniciando: " + context.getMethod().getName());

        Object result = context.proceed();

        System.out.println("Finalizando: " + Arrays.asList(context.getParameters()));

        return result;
    }
}