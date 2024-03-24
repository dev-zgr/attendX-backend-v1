package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "StudentDTO",
        description = "Schema to hold Student information"
)
@Data
@NoArgsConstructor
public class StudentDTO extends UserBaseDTO{

    private String studentNumber;

    public StudentDTO(String firstName, String lastName, String email, String password, String phoneNumber, AddressDTO address, String studentNumber,String role) {
        super(firstName, lastName, email, password, phoneNumber, address,role);
        this.studentNumber =  studentNumber;
    }



}
