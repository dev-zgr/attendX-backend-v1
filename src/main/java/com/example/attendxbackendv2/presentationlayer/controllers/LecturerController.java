package com.example.attendxbackendv2.presentationlayer.controllers;

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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Lecturer",
        description = "Lecturer API endpoints")
@RestController
@RequestMapping(value = "/api/v1" , produces = "application/json")
@Validated
public class LecturerController {

    private final LecturerService lecturerService;


    @Autowired
    public LecturerController(LecturerService lecturerService){
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
    public ResponseEntity<ResponseDTO> createLecturer(@Valid @RequestBody LecturerDTO lecturerDTO){
        lecturerService.createLecturer(lecturerDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
                Integer.toString(HttpStatus.CREATED.value()),
                LecturerContents.MESSAGE_201));

    }
}
