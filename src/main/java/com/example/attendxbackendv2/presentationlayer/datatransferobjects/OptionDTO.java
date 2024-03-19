package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class OptionDTO {
    private String value;
    private String label;
}
