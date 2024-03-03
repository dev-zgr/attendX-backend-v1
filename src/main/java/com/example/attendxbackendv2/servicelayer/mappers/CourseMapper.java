package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.CourseEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CourseMapper {



    public static CourseEntity mapToCourseEntity(CourseEntity courseEntity, CourseDTO courseDTO) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        courseEntity.setCourseCode(courseDTO.getCourseCode());
        courseEntity.setCourseName(courseDTO.getCourseName());
        courseEntity.setDescription(courseDTO.getDescription());
        courseEntity.setStartDate(LocalDate.parse(courseDTO.getStartDate(), formatter));
        courseEntity.setEndDate(LocalDate.parse(courseDTO.getEndDate(), formatter));

        return courseEntity;
    }

    public static CourseDTO mapToCourseDTO(CourseEntity courseEntity, CourseDTO courseDTO,  boolean getDetails) {
        courseDTO.setCourseCode(courseEntity.getCourseCode());
        courseDTO.setCourseName(courseEntity.getCourseName());
        courseDTO.setDepartmentName(courseEntity.getDepartment().getDepartmentName());
        if (getDetails) {
            courseDTO.setDescription(courseEntity.getDescription());
            courseDTO.setStartDate(courseEntity.getStartDate().toString());
            courseDTO.setEndDate(courseEntity.getEndDate().toString());
            courseDTO.setLecturerEmail(courseEntity.getLecturer().getEmail());
            courseDTO.setEnrolledStudents(courseEntity.getEnrolledStudents().stream().map(studentEntity -> StudentMapper.mapStudentEntityToStudentDTO(studentEntity,
                    new StudentDTO(),
                    new AddressDTO(),
                    false )).toList());
        }
        return courseDTO;
    }
}
