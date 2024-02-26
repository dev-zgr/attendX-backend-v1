package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "AddressDTO",
        description = "Schema to hold Address information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    @NotBlank(message = "Street first line cannot be blank")
    @Size(max = 255, message = "Street first line must be less than or equal to 255 characters")
    @Schema(
            description = "An example of street first line registered in attendX",
            example = "416 Lake Crescent rd"
    )
    private String streetFirstLine;
    @Schema(
            description = "An example of street second line registered in attendX",
            example = "Employee Housing Dorm W-10"
    )
    @Size(max = 255, message = "Street second line must be less than or equal to 255 characters")
    private String streetSecondLine;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City must be less than or equal to 100 characters")
    @Schema(
            description = "An example of city registered in attendX",
            example = "Port Angeles"
    )
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Size(max = 100, message = "State must be less than or equal to 100 characters")
    @Schema(
            description = "An example of state registered in attendX",
            example = "Washington"
    )
    private String state;

    @NotBlank(message = "Country cannot be blank")
    @Size(max = 100, message = "Country must be less than or equal to 100 characters")
    @Schema(
            description = "An example of country registered in attendX",
            example = "United States"
    )
    private String country;

    @NotBlank(message = "Zip code cannot be blank")
    @Pattern(regexp = "\\d{5}", message = "Zip code must be a 5-digit number")
    @Schema(
            description = "An example of zip code registered in attendX",
            example = "98363"
    )
    private String zipCode;
}

