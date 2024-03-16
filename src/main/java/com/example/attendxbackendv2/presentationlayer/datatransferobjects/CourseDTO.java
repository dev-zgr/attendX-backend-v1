package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Schema(
        name = "CourseDTO",
        description = "Schema to hold Course information"
)
@Data
public class CourseDTO {
    @NotEmpty(message = "Course code cannot be null or empty")
    @Pattern(regexp = "^[A-Z]{3}[0-9]{3}$", message = "Course code should be in the format of AAA111")
    @Schema(
            description = "Stores the course code",
            example =  "YMH212"
    )
    private String courseCode;

    @NotEmpty(message = "Course name cannot be null or empty")
    @Size(min = 8 , max = 60, message = "Course name should be between 8 and 60 characters")
    @Schema(
            description = "Stores the course name",
            example =  "Introduction to Programming"
    )
    private String courseName;

    @NotEmpty(message = "Description cannot be null or empty")
    @Size(min = 16 , max = 256, message = "Description should be between 8 and 256 characters")
    @Schema(
            description = "Stores the course description",
            example =  "This course introduces students to the basics of programming"
    )
    private String description;

    @NotNull(message = "Lecturer cannot be null")
    @NotEmpty(message = "Lecturer email cannot be null or empty")
    @Schema(
            description = "Stores the lecturer of the course",
            example = "JohnDoe@john.com"
    )
    @Email(message = "Invalid email format")
    private String lecturerEmail;

    @NotNull(message = "Start date cannot be null")
    @Schema(
            description = "Stores the start date of the course in DD-MM-YYYY format",
            example =  "09-01-2022"
    )
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d\\d$", message = "Date should be in the format of DD-MM-YYYY")
    private String startDate;

    @NotNull(message = "End date cannot be null")
    @Schema(
            description = "Stores the end date of the course in DD-MM-YYYY format",
            example =  "09-05-2022"
    )
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(19|20)\\d\\d$", message = "Date should be in the format of DD-MM-YYYY")
    private String endDate;

    @NotNull(message = "Department cannot be null")
    @Schema(
            description = "Stores the department of the course",
            example = "Engineering Department"
    )
    private String departmentName;

    @Schema(
            description = "Stores the students enrolled in the course",
            implementation =  StudentDTO.class
    )
    private List<StudentDTO> enrolledStudents;

    @Schema(
            description = "Stores the sessions of the course",
            implementation =  SessionDTO.class
    )
    private List<SessionDTO> courseSessions;
}
