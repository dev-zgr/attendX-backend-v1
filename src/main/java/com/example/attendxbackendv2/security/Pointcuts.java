package com.example.attendxbackendv2.security;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.LecturerController.*(..))")
    public void secureLecturerController() {}
    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.DepartmentController.createDepartment(..))")
    public void createDepartment() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.DepartmentController.updateDepartment(..))")
    public void updateDepartmentId() {}

}
