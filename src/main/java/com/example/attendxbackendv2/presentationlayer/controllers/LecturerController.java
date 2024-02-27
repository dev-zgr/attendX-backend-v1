package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.servicelayer.contants.LecturerContents;
import com.example.attendxbackendv2.servicelayer.interfaces.LecturerService;
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
        name = "Lecturer",
        description = "Lecturer API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
public class LecturerController {

    private final LecturerService lecturerService;


    @Autowired
    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
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
    @PostMapping(path = "/lecturer", consumes = "application/json")
    public ResponseEntity<ResponseDTO> createLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) {
        lecturerService.createLecturer(lecturerDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
                Integer.toString(HttpStatus.CREATED.value()),
                LecturerContents.MESSAGE_201));

    }

    @Operation(
            summary = "Fetch All Departments REST API",
            description = "Fetch all department details from the attendX application" +
                    "This will be mainly used to show all the departments in the UI"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = LecturerDTO.class)
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
    @GetMapping(path = "/lecturer", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<LecturerDTO>> getAllDepartments(
            @RequestParam(value = "page-no", defaultValue = "0") int pageNo,
            @RequestParam(value = "ascending", defaultValue = "true") boolean ascending) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lecturerService.getAllLecturers(pageNo, ascending));
    }


    @Operation(
            summary = "Fetch Lecturer by email REST API",
            description = "Fetch lecturer details by ID from the attendX application" +
                    "This will be mainly used to show all the lecturer details in the UI" +
                    "This section fetches the email, first and last name, password, phone" +
                    " number, department, and address of the lecturer"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = DepartmentDTO.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status Not Found it may be causing due trying to access non-existing lecturer",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            })
    @GetMapping(path = "/lecturer/{email}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LecturerDTO> getLecturerByEmail(@PathVariable String email, @RequestParam(value = "get-details", defaultValue = "true") boolean getDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lecturerService.getLecturerByEmail(email, getDetails));
    }

    @Operation(
            summary = "Update the Lecturer with REST API",
            description = "Update Lecturer Details in AttendX application"
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
                                   "non-existing lecturer or department",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),@ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation failed  it may occurs due anomalies in AttendX system",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                                )
                    )
            }
    )
    @PutMapping(path = "/lecturer",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) {
        boolean isUpdated = lecturerService.updateLecturer(lecturerDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(LecturerContents.STATUS_200, LecturerContents.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LecturerContents.STATUS_417, LecturerContents.MESSAGE_417_UPDATE));
        }
    }


}
