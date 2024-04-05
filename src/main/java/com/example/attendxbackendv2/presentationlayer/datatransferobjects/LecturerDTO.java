package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
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

    @Schema(
            description = "Stores the courses given by the lecturer",
            implementation =  StudentDTO.class
    )
    private List<CourseDTO> courses;

    public LecturerDTO(String firstName, String lastName, String email, String password, String phoneNumber, AddressDTO address,String department, String role) {
        super(firstName, lastName, email, password, phoneNumber, address, role);
        this.department = department;
    }

}
