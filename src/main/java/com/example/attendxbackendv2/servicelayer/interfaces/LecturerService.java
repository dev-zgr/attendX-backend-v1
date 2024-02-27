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
}
