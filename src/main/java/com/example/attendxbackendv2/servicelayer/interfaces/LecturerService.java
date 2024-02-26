package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.LecturerAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;

public interface LecturerService {
    /**
     * Create department.
     * it may throw LecturerAlreadyExistException if any lecturer registered using the same e-mail
     * ResourceNotFoundException if no such department found with the specified department name
     * @param lecturerDTO  Lecturer DTO to be crated

     */
    void createLecturer(LecturerDTO lecturerDTO) throws LecturerAlreadyExistException, ResourceNotFoundException;
}
