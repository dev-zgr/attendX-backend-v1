package com.example.attendxbackendv2.datalayer.entities;

import com.example.attendxbackendv2.interfaces.SelectableInterface;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@DiscriminatorValue("LECTURER")
@Data
public class LecturerEntity extends UserBaseEntity implements SelectableInterface {



    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity registeredDepartment;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CourseEntity> courses;

    public LecturerEntity(String firstName, String lastName, String email, String phoneNumber,String password, AddressEmbeddable address,
                          DepartmentEntity departmentEntity){
        super(firstName,lastName,email,password,phoneNumber,address);
        this.registeredDepartment = departmentEntity;
        this.courses = new ArrayList<>();
    }

    public LecturerEntity(){
        super();
        this.courses = new ArrayList<>();
        this.registeredDepartment = null;
    }

    public void addCourse(CourseEntity courseEntity){
        this.courses.add(courseEntity);
    }

    public void removeCourse(CourseEntity courseEntity){
        courses.removeIf(course -> course.getCourseCode().equals(courseEntity.getCourseCode()));
    }

    @Override
    public String getIdentifier() {
        return this.getEmail();
    }

    @Override
    public String getLabel() {
        return String.format("%s %s", this.getFirstName(), this.getLastName());
    }
}


