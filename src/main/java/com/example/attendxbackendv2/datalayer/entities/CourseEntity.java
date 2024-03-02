package com.example.attendxbackendv2.datalayer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_code", nullable = false, unique = true)
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "Course code should be in the format of AAA111")
    private String courseCode;

    @Column(name = "course_name", nullable = false)
    @Size(min = 8 , max = 60, message = "Course name should be between 8 and 60 characters")
    private String courseName;

    @Column(name = "description", nullable = false)
    @Size(min = 16 , max = 256, message = "Description should be between 8 and 256 characters")
    private String description;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference DepartmentEntity department;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<StudentEntity> enrolledStudents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private LecturerEntity lecturer;

    public CourseEntity(String courseCode, String courseName, String description, LocalDate startDate, LocalDate endDate, DepartmentEntity department, LecturerEntity lecturerEntity){
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.enrolledStudents = new ArrayList<>();
        this.lecturer =lecturerEntity;
    }

    public CourseEntity() {
        this.enrolledStudents = new ArrayList<>();
        this.lecturer = null;
        this.courseCode = null;
        this.courseName = null;
        this.description = null;
        this.startDate = null;
        this.endDate = null;
        this.department = null;
    }

    public void enrollStudent(StudentEntity studentEntity){
        this.enrolledStudents.add(studentEntity);
    }

    public void unrollStudent(StudentEntity studentEntity){
        enrolledStudents.removeIf(student -> student.getEmail().equals(studentEntity.getEmail()));
    }


}
