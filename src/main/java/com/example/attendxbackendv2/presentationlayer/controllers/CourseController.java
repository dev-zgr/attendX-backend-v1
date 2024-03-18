package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;
import com.example.attendxbackendv2.servicelayer.contants.CourseConstants;
import com.example.attendxbackendv2.servicelayer.interfaces.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Course API endpoints",
        description = "Course API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
@CrossOrigin(origins = "${attendx.crossorigin.url}")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @Operation(
            summary = "Creates a Course Entity with REST APIs",
            description = "Create a new Course in the AttendX application "
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping(path = "/course" ,
    consumes = {MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<ResponseDTO> createCourse(@RequestBody CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(
                CourseConstants.STATUS_201,
                CourseConstants.MESSAGE_201));

    }


    @Operation(
            summary = "Fetch All Courses REST API",
            description = "Fetch all Course details from the attendX application" +
                    "This will be mainly used to show all the departments in the UI"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = StudentDTO.class)
                            )
                    ), @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )

            }
    )
    @GetMapping(path = "/course", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<CourseDTO>> getAllCourses(
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.getAllCourses(pageNo, ascending));
    }


    @Operation(
            summary = "Fetch Course by course code REST API",
            description = "Course student details by email from the attendX application. " +
                    "This will be mainly used to show all the course details in the UI. " +
                    "This section fetches the course code, course name, course description course lecturer's email" +
                    "course start date, course end date, and department name of the course."
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = StudentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "HTTP Status Bad Request it may be causing due to invalid input",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status Not Found it may be causing due to trying to access non-existing course",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            })
    @GetMapping(path = "/course/{courseCode}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CourseDTO> getStudentByEmail(@PathVariable String courseCode, @RequestParam(value = "get-details", defaultValue = "true") boolean getDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.getCourseByCourseCode(courseCode, getDetails));
    }


    @Operation(
            summary = "Update Course REST API",
            description = "Update the existing Course in the AttendX application"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request it may be causing due to invalid input",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found it may be causing due to trying to access non-existing course",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping(path = "/course",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<ResponseDTO> updateCourse(@Valid @RequestBody CourseDTO courseDTO) {
        boolean isCourseUpdated = courseService.updateCourse(courseDTO);
        if (isCourseUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                    CourseConstants.STATUS_200, CourseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDTO(
                            CourseConstants.STATUS_417, CourseConstants.MESSAGE_417_UPDATE)
            );
        }
    }

    @Operation(
            summary = "Enroll Student Course REST API",
            description = "Update the Student and Course in the AttendX application to enroll the student to the course."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found it may be causing due to trying to access non-existing course",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PatchMapping(path = "/course",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<ResponseDTO> enrollToCourse(@RequestParam(name = "course-code") String courseCode,
                                                      @RequestParam(name = "student-id") String studentID) {
        boolean isCourseUpdated = courseService.enrollStudent(courseCode,studentID);
        if (isCourseUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(
                    CourseConstants.STATUS_200, CourseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDTO(
                            CourseConstants.STATUS_417, CourseConstants.MESSAGE_417_UPDATE)
            );
        }
    }

}
