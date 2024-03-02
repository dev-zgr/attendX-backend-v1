package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.CourseEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;

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
}
