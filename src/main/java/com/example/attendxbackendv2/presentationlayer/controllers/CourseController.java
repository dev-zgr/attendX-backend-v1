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
    @PostMapping(path = "/course", consumes = "application/json")
    public ResponseEntity<ResponseDTO> createCourse( @RequestBody CourseDTO courseDTO) {
        courseService.createCourse(courseDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
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

}
