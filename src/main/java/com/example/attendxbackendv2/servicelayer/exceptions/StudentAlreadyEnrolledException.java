package com.example.attendxbackendv2.servicelayer.exceptions;
public class StudentAlreadyEnrolledException extends RuntimeException {
    public StudentAlreadyEnrolledException(String studentID, String courseCode) {
        super(String.format("Student with id: %s has already registered to course: %s", studentID, courseCode));
    }
}