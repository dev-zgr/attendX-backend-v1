package com.example.attendxbackendv2.datalayer.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@EqualsAndHashCode(callSuper = false)
@ToString
@Entity
@Data
public class EditorEntity extends UserBaseEntity {

    public EditorEntity(String firstName, String lastName, String email, String phoneNumber,String password, AddressEmbeddable address){

        super(firstName,lastName,email,password,phoneNumber,address);
    }

    public EditorEntity(){
        super();
    }
}
