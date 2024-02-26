package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "DepartmentDTO",
        description = "Schema to hold Department information"
)
@Data
public class DepartmentDTO {

    @NotEmpty(message = "Department name can not be a null or empty")
    @Size(min = 8 , max = 60, message = "Department name should be between 8 and 60 characters")
    @Schema(
            description = "An example of department registered in attendX",
            example = "Department of Engineering"
    )
    private String departmentName;


    @NotEmpty(message = "Description name can not be a null or empty")
    @Size(min = 16 , max = 256, message = "Description should be between 8 and 60 characters")
    @Schema(
            description = "An example of department  description registered in attendX",
            example = "Department of Engineering serves as a hub for research and education in the field of engineering and technology."
    )
    private String description;
}



