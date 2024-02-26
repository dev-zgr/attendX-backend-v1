package com.example.attendxbackendv2.servicelayer.interfaces;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.DepartmentAlreadyExistsException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The interface Department service.
 */
public interface DepartmentService {

    /**
     * Create department.
     *
     * @param departmentDTO  the department name

     */
    void createDepartment(DepartmentDTO departmentDTO) throws DepartmentAlreadyExistsException;


    /**
     * Fetch department details by department name string.
     *
     * @param departmentName the department name
     * @param fetchDetails   the fetch details. If true, fetches the department details
     * @return the string
     */
    DepartmentDTO fetchDepartmentDetailsByDepartmentName(String departmentName, boolean fetchDetails) throws ResourceNotFoundException;


    /**
     * Create department.
     */
    boolean updateDepartmentId(DepartmentDTO departmentDTO) throws DepartmentAlreadyExistsException, ResourceNotFoundException;

    /**
     * Delete department.
     *
     * @param departmentName the department name
     */
    boolean deleteDepartment(String departmentName) throws ResourceNotFoundException;


     List<DepartmentDTO> getAllDepartments(int pageNo, boolean ascending);



}
