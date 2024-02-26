package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * The DepartmentEntity is a simple POJO class that has the following attributes:
 * departmentId, departmentName, description
 * The class is a simple
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class DepartmentEntity{
    /**
     * The departmentId is a unique identifier for the department
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")

    private Long departmentId;

    /**
     * The departmentName is the name of the department
     * constraints: not null, unique
     * length: 8-60
     */
    @Column(name = "department_name", nullable = false, unique = true)
    @Size(min = 8 , max = 60, message = "Department name should be between 8 and 60 characters")
    private String departmentName;


    /**
     * The description is the description of the department
     * constraints: not null
     * length: 16-256
     */
    @Column(name = "description", nullable = false)
    @Size(min = 16 , max = 256, message = "Description should be between 8 and 60 characters")
    private String description;
}
