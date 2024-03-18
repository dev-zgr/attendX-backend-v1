package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Map;

@Schema(
        name = "SessionDTO",
        description = "Schema to hold Session information"
)
@Data
public class SessionDTO {

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d\\d$", message = "Date should be in the format of DD-MM-YYYY")
    private String sessionDate;

    private Long sessionId;

//    @Schema(
//            description = "Stores attendance of the students in the session",
//            example =  "John Doe: true, Jane Doe: false, ..."
//    )
//    @NotNull(message = "End date cannot be null")
//    private Map<StudentDTO,Boolean> attendance;



}
