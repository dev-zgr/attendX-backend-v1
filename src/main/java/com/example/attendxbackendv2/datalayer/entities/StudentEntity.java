package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Data
public class StudentEntity extends UserBaseEntity {

    @Column(name = "student_id", nullable = false, unique = true)
    private String studentId =  Integer.toString(studentIdGenerator());

    public StudentEntity(String firstName, String lastName, String email, String phoneNumber,String password, AddressEmbeddable address){

        super(firstName,lastName,email,password,phoneNumber,address);
    }

    public StudentEntity(){
        super();
    }


    private static int studentIdGenerator(){
        return 22290000 + (int) (Math.random() * 100);
    }
}
