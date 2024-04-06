package com.example.attendxbackendv2.security;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.LecturerController.*(..))")
    public void secureLecturerController() {}
    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.DepartmentController.createDepartment(..))")
    public void createDepartment() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.DepartmentController.updateDepartment(..))")
    public void updateDepartmentId() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.StudentController.*(..))")
    public void allMethodsInStudentController() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.StudentController.createStudent(..))")
    public void createStudentInStudentController() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.CourseController.createCourse(..))")
    public void secureCreateCourse() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.CourseController.*(..))")
    public void allMethodsInCourseController() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.CourseController.getAllCourses(..))")
    public void secureGetCourse() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.CourseController.updateCourse(..))")
    public void updateCourseInCourseController() {}

    @Pointcut("execution(* com.example.attendxbackendv2.presentationlayer.controllers.CourseController.enrollToCourse(..))")
    public void enrollCourseInCourseController(){}

}


