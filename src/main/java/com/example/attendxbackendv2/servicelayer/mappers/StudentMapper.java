package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.datalayer.entities.StudentEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;

public class StudentMapper {


    public static StudentEntity mapStudentDTOToStudentEntity(StudentEntity studentEntity, StudentDTO studentDTO, AddressEmbeddable addressEmbeddable) {
        return   UserGenericMapper.mapUserDTOToUserEntity(studentEntity, studentDTO,addressEmbeddable);
    }

    public static StudentDTO mapStudentEntityToStudentDTO(StudentEntity studentEntity, StudentDTO studentDTO, AddressDTO addressDTO, boolean getDetails){
        StudentDTO studentDTOToReturn = UserGenericMapper.mapUserEntityToUserDTO(studentEntity, studentDTO,addressDTO , getDetails);
        studentDTOToReturn.setStudentNumber(studentEntity.getStudentId());
        return  studentDTOToReturn;
    }

}
