package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "LecturerDTO",
        description = "Schema to hold Lecturer information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO extends UserBaseDTO{

    @Schema(
            description = "Stores the email  of the lecturer",
            example =  "Engineering"
    )
    @NotEmpty(message = "Department cannot be null or empty")
    @Size(max = 100, message = "Department must be less than or equal to 100 characters")
    private String department;

    public LecturerDTO(String firstName, String lastName, String email, String password, String phoneNumber, AddressDTO address,String department, String role) {
        super(firstName, lastName, email, password, phoneNumber, address, role);
        this.department = department;
    }



}
