package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDTO {

    @Schema(
            description = "Stores the first name of the lecturer",
            example =  "John"
    )
    @NotEmpty(message = "Lecturer name cannot be null or empty")
    @Size(max = 60, message = "Lecturer name should not exceed 60 characters")
    private String firstName;

    @Schema(
            description = "Stores the first name of the lecturer",
            example =  "Doe"
    )
    @Size(max = 60, message = "Last name must be less than or equal to 60 characters")
    private String lastName;

    @Schema(
            description = "Stores the email  of the lecturer",
            example =  "john@doe.com"
    )
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Stores the email  of the lecturer",
            example =  "password"
    )
    @Size(min = 8, max = 16,  message = "Password should be at least 8 and 16 characters")
    @NotEmpty
    private String password;

    @Schema(
            description = "Stores the email  of the lecturer",
            example =  "3604882343"
    )
    @Pattern(regexp = "\\d{10}", message = "Invalid phone number format")
    private String phoneNumber;

    @Schema(
            description = "An example of address registered in attendX"
    )
    @Valid
    private AddressDTO address;
}
