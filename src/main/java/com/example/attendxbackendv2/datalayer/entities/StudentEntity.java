package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Data
public class StudentEntity extends UserBaseEntity {

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId =  Integer.toString(studentIdGenerator());

    @ManyToMany(mappedBy = "enrolledStudents")
    private List<CourseEntity> enrolledCourses;

    public StudentEntity(String firstName, String lastName, String email, String phoneNumber,String password, AddressEmbeddable address){

        super(firstName,lastName,email,password,phoneNumber,address);
        this.enrolledCourses = new ArrayList<>();
    }

    public StudentEntity(){
        super();
        this.enrolledCourses = new ArrayList<>();
    }


    private static int studentIdGenerator(){
        return 22290000 + (int) (Math.random() * 100);
    }

    public void enrollToCourse(CourseEntity courseEntity){
        this.enrolledCourses.add(courseEntity);
    }

    public void unrollFromCourse(CourseEntity courseEntity){
        enrolledCourses.removeIf(course -> course.getCourseCode().equals(courseEntity.getCourseCode()));
    }
}
