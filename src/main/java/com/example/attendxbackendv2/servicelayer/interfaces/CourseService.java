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

    /**
     * Fetch course details by course code .
     * @param courseCode the course code of the requested course
     * @param getDetails the fetch details. If true, fetches the course details. If false, fetches only the course code,
     *                  course name and department name of the course.Fewer data increases resilience for the application
     *                   when presenting the data
     * @return the requested CourseDTO if found
     * @throws ResourceNotFoundException if no such course found with the specified code
     */
    CourseDTO getCourseByCourseCode(String courseCode, boolean getDetails) throws ResourceNotFoundException;

    /**
     * Updates the existing Course by fetching it from the database and applying recent changes.
     *
     * @param courseDTO The DTO containing the Course's code as an identifier and updated fields.
     * @return True if the update was successful.
     * @throws ResourceNotFoundException May be thrown if attempting to access a non-existing student
     *                                   or trying to change the department of the student to a non-existing department.
     */
    boolean updateCourse(CourseDTO courseDTO);
}

