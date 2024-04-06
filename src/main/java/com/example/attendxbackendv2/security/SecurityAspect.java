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

    @Before("com.example.attendxbackendv2.security.Pointcuts.secureLecturerController() || " +
            "com.example.attendxbackendv2.security.Pointcuts.createStudentInStudentController() || " +
            "com.example.attendxbackendv2.security.Pointcuts.secureCreateCourse() || " +
            "execution(* com.example.attendxbackendv2.presentationlayer.controllers.EditorController.*(..)) || " +
            "com.example.attendxbackendv2.security.Pointcuts.createDepartment() || " +
            "com.example.attendxbackendv2.security.Pointcuts.updateDepartmentId()")
    public void combinedPointcutExpression() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null || !loginService.validateToken(token).equalsIgnoreCase("EDITOR")) {
            throw new InvalidCredentialsException("Invalid Token");
        }
    }

    @Before("com.example.attendxbackendv2.security.Pointcuts.allMethodsInStudentController() || " +
            "com.example.attendxbackendv2.security.Pointcuts.updateCourseInCourseController() && " +
            "!com.example.attendxbackendv2.security.Pointcuts.createStudentInStudentController()")
    public void secureStudentController(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null ||
                !(loginService.validateToken(token).equalsIgnoreCase("EDITOR") ||
                loginService.validateToken(token).equalsIgnoreCase("LECTURER"))) {
            throw new InvalidCredentialsException("Invalid Token");
        }
    }


    @Before("com.example.attendxbackendv2.security.Pointcuts.allMethodsInCourseController() && " +
            "!com.example.attendxbackendv2.security.Pointcuts.secureCreateCourse() && " +
            "!com.example.attendxbackendv2.security.Pointcuts.updateCourseInCourseController() && " +
            "!com.example.attendxbackendv2.security.Pointcuts.enrollCourseInCourseController()")
    public void secureCourseEndpoints(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null ||
                !(loginService.validateToken(token).equalsIgnoreCase("EDITOR") ||
                        loginService.validateToken(token).equalsIgnoreCase("LECTURER") ||
                        loginService.validateToken(token).equalsIgnoreCase("STUDENT"))) {
            throw new InvalidCredentialsException("Invalid Token");
        }
    }



    @Before("com.example.attendxbackendv2.security.Pointcuts.enrollCourseInCourseController()")
    public void secureEnrollingCourse(JoinPoint joinPoint){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");

        if (token == null ||
                !(loginService.validateToken(token).equalsIgnoreCase("STUDENT"))) {
            throw new InvalidCredentialsException("Invalid Token");
        }
    }
}
