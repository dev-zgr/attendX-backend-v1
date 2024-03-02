package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.CourseAlreadyExistsException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;

public interface CourseService {

    /**
     * Creates department with given Course DTO.
     * It may throw ResourceNotFoundException if the department  or Lecturer does not exist
     * It may throw CourseAlreadyExistsException if the course already exists
     * @param courseDTO  course DTO to be Create
     */
    void createCourse(CourseDTO courseDTO) throws ResourceNotFoundException, CourseAlreadyExistsException;
}

