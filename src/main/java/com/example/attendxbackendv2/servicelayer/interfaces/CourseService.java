package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.CourseAlreadyExistsException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CourseService {

    /**
     * Creates department with given Course DTO.
     * It may throw ResourceNotFoundException if the department  or Lecturer does not exist
     * It may throw CourseAlreadyExistsException if the course already exists
     * @param courseDTO  course DTO to be Create
     */
    void createCourse(CourseDTO courseDTO) throws ResourceNotFoundException, CourseAlreadyExistsException;

    /**
     * Get all the Courses for UI presentation it doesn't fetches the details of the Courses
     * It uses pagination and sorting by Course code.
     * @param pageNo page number of the result. See application.properties for the page size
     * @param ascending sorting order
     * @return the list of the CourseDTO requested
     */
    List<CourseDTO> getAllCourses(int pageNo, boolean ascending);

}

