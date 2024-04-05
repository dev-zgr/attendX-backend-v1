package com.example.attendxbackendv2.security;

import com.example.attendxbackendv2.servicelayer.exceptions.InvalidCredentialsException;
import com.example.attendxbackendv2.servicelayer.interfaces.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SecurityAspect {

    private final LoginService loginService;

    @Autowired
    SecurityAspect(LoginService loginService) {
        this.loginService = loginService;
    }

    @Before("execution(* com.example.attendxbackendv2.presentationlayer.controllers.EditorController.*(..))")
    public void beforeControllerMethodExecution(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null || !loginService.validateToken(token).equalsIgnoreCase("EDITOR")) {
            throw new InvalidCredentialsException("Invalid Token");
        }
    }
}
