package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.GenericListResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;
import com.example.attendxbackendv2.servicelayer.contants.StudentConstants;
import com.example.attendxbackendv2.servicelayer.interfaces.StudentService;
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

@Tag(
        name = "Student",
        description = "Student API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
@CrossOrigin(origins = "${attendx.crossorigin.url}")
public class StudentController {

    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @Operation(
            summary = "Creates a Lecturer Entity with REST APIs",
            description = "Create a new lecturer in the AttendX application "
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
    @PostMapping(path = "/student", consumes = "application/json")
    public ResponseEntity<ResponseDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        studentService.createStudent(studentDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
                Integer.toString(HttpStatus.CREATED.value()),
                StudentConstants.MESSAGE_201));
    }


    @Operation(
            summary = "Fetch All Students REST API",
            description = "Fetch all student details from the attendX application" +
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
    @GetMapping(path = "/student", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<GenericListResponseDTO<StudentDTO>> getAllStudents(
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending) {
        GenericListResponseDTO<StudentDTO> response  = new GenericListResponseDTO<>();
        response.setData(studentService.getAllStudents(pageNo, ascending));
        response.setPageNumber(studentService.getPageCount());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @Operation(
            summary = "Fetch Student by email REST API",
            description = "Fetch student details by email from the attendX application. " +
                    "This will be mainly used to show all the student details in the UI. " +
                    "This section fetches the email, first and last name, password, phone " +
                    "number, and address of the student."
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
                            description = "HTTP Status Not Found it may be causing due to trying to access non-existing student",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            })
    @GetMapping(path = "/student/{email}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StudentDTO> getStudentByEmail(@PathVariable String email, @RequestParam(value = "get-details", defaultValue = "true") boolean getDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentService.getStudentByEmail(email, getDetails));
    }

    @Operation(
            summary = "Update the Student with REST API",
            description = "Update Student Details in AttendX application"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "202",
                            description = "HTTP Status Accepted",
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
                            description = "HTTP Status Bad Request it may be causing due to invalid input or department not found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ), @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status Not Found it may be causing due trying to access " +
                            "non-existing student or department",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),@ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation failed  it may occur due to anomalies in AttendX system",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
            })
    @PutMapping(path = "/student",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateStudent(@Valid @RequestBody StudentDTO studentDTO) {
        boolean isUpdated = studentService.updateStudent(studentDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(new ResponseDTO(StudentConstants.STATUS_200, StudentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(StudentConstants.STATUS_417, StudentConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Student REST API",
            description = "Delete Student from the AttendX application"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "202",
                            description = "HTTP Status ACCEPTED",
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
                            responseCode = "417",
                            description = "HTTP Status Expectation failed  it may occurs due anomalies in AttendX system",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @DeleteMapping(path = "/student/{email}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> deleteStudent(@PathVariable String email) {
        boolean isDeleted = studentService.deleteStudent(email);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(StudentConstants.STATUS_200, StudentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(StudentConstants.STATUS_417, StudentConstants.MESSAGE_417_UPDATE));
        }
    }




}
