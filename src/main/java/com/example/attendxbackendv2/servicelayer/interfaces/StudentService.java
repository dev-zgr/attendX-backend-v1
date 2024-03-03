package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.exceptions.StudentAlreadyExistException;

import java.util.List;

public interface StudentService {
    /**
     * Create department.
     * it may throw StudentAlreadyExistException if any lecturer registered using the same e-mail
     * ResourceNotFoundException if no such department found with the specified department name
     * @param studentDTO  Lecturer DTO to be crated

     */
    void createStudent(StudentDTO studentDTO) throws StudentAlreadyExistException, ResourceNotFoundException;

    /**
     * Get all the Students for UI presentation it doesn't fetches the details of the students
     * It uses pagination and sorting by instructors first name.
     * @param pageNo page number of the result. See application.properties for the page size
     * @param ascending sorting order
     * @return the list of the StudentDTOs requested
     */
    List<StudentDTO> getAllStudents(int pageNo, boolean ascending);

    /**
     * Fetch student details by email string.
     *
     * @param email      the email of the requested student
     * @param getDetails the fetch details. If true, fetches the student details. If false, fetches only the email and name
     *                   of the student. Less data increases resilience for the application when presenting the data
     * @return the requested StudentDTO if found
     * @throws ResourceNotFoundException if no such student found with the specified email
     */
    StudentDTO getStudentByEmail(String email, boolean getDetails) throws ResourceNotFoundException;

    /**
     * Updates the existing Student by fetching it from the database and applying recent changes.
     *
     * @param studentDTO The DTO containing the student's email as an identifier and updated fields.
     * @return True if the update was successful.
     * @throws ResourceNotFoundException May be thrown if attempting to access a non-existing student
     *                                   or trying to change the department of the student to a non-existing department.
     */
    boolean updateStudent(StudentDTO studentDTO);

    /**
     * Deletes the student by its email. This method is subject to update, currently
     * it performs the deletion logic for students.
     *
     * @param email the email of the requested student to be deleted.
     * @return true if the entity is deleted successfully, false otherwise.
     */
    boolean deleteStudent(String email);

}

