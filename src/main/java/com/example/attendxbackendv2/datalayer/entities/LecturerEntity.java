package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Data
public class LecturerEntity extends UserBaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private DepartmentEntity registeredDepartment;

    public LecturerEntity(String firstName, String lastName, String email, String phoneNumber,String password, AddressEmbeddable address,
                          DepartmentEntity departmentEntity){
        super(firstName,lastName,email,password,phoneNumber,address);
        this.registeredDepartment = departmentEntity;
    }


}


