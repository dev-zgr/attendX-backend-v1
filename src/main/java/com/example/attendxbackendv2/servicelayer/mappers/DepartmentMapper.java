package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import org.springframework.stereotype.Component;

public class DepartmentMapper {
    public static DepartmentDTO mapToDepartmentDTO(DepartmentEntity departmentEntity, DepartmentDTO departmentDTO) {
        departmentDTO.setDepartmentName(departmentEntity.getDepartmentName());
        departmentDTO.setDescription(departmentEntity.getDescription());
       return departmentDTO;
    }

    public static DepartmentEntity mapToDepartmentEntity(DepartmentDTO departmentDTO, DepartmentEntity departmentEntity) {
        departmentEntity.setDepartmentName(departmentDTO.getDepartmentName());
        departmentEntity.setDescription(departmentDTO.getDescription());
        return departmentEntity;
    }
}
