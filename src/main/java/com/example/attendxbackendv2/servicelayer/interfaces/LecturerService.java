package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.LecturerAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;

import java.util.List;

public interface LecturerService {
    /**
     * Create department.
     * it may throw LecturerAlreadyExistException if any lecturer registered using the same e-mail
     * ResourceNotFoundException if no such department found with the specified department name
     * @param lecturerDTO  Lecturer DTO to be crated

     */
    void createLecturer(LecturerDTO lecturerDTO) throws LecturerAlreadyExistException, ResourceNotFoundException;

    /**
     * Get all the lecturer for UI presentation it doesn't fetches the details of the lecturer
     * It uses pagination and sorting by instructors first name.
     * @param pageNo page number of the result. See application.properties for the page size
     * @param ascending sorting order
     * @return
     */
    List<LecturerDTO> getAllLecturers(int pageNo, boolean ascending);

    /**
     * Fetch lecturer details by email string.
     * @param email the email of the requested lecturer
     * @param getDetails the fetch details. If true, fetches the lecturer details. If false, fetches only the email and name
     *                   of the lecturer. Less lecture increases resilience for the application when presenting the data
     *
     * @return the requested LecturerDTO if found
     * @throws ResourceNotFoundException if no such lecturer found with the specified email
     */
    LecturerDTO getLecturerByEmail(String email, boolean getDetails) throws ResourceNotFoundException;
}
