package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.EditorDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.servicelayer.contants.EditorConstants;
import com.example.attendxbackendv2.servicelayer.interfaces.EditorService;
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
        name = "Editor",
        description = "Editor API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
public class EditorController {

    private final EditorService editorService;

    @Autowired
    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @Operation(
            summary = "Creates an Editor Entity with REST APIs",
            description = "Create a new editor in the AttendX application "
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
    @PostMapping(path = "/editor", consumes = "application/json")
    public ResponseEntity<ResponseDTO> createEditor(@Valid @RequestBody EditorDTO editorDTO) {
        editorService.createEditor(editorDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
                Integer.toString(HttpStatus.CREATED.value()),
                EditorConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch All Editors REST API",
            description = "Fetch all editor details from the attendX application"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = EditorDTO.class)
                            )
                    ), @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    ))
            }

    )
    @GetMapping(path = "/editor", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<EditorDTO>> getAllEditors(
            @RequestParam(value = "page-no", defaultValue = "0")
            int pageNo,
            @RequestParam(value = "ascending", defaultValue = "true")
            boolean ascending) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(editorService.getAllEditors(pageNo, ascending));
    }

    @Operation(
            summary = "Fetch Editor by email REST API",
            description = "Fetch editor details by email from the attendX application."
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "HTTP Status OK",
                            content = @Content(
                                    schema = @Schema(implementation = EditorDTO.class)
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
                            description = "HTTP Status Not Found it may be causing due to trying to access non-existing editor",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            })
    @GetMapping(path = "/editor/{email}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EditorDTO> getEditorByEmail(@PathVariable String email,@RequestParam(value = "get-details", defaultValue = "true") boolean getDetails) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(editorService.getEditorByEmail(email, getDetails));
    }

    @Operation(
            summary = "Update the Editor with REST API",
            description = "Update Editor Details in AttendX application"
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
                            "non-existing editor or department",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ), @ApiResponse(
                    responseCode = "417",
                    description = "HTTP Status Expectation failed  it may occur due to anomalies in AttendX system",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
            })
    @PutMapping(path = "/editor",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateEditor(@Valid @RequestBody EditorDTO editorDTO) {
        boolean isUpdated = editorService.updateEditor(editorDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(EditorConstants.STATUS_200, EditorConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(EditorConstants.STATUS_417, EditorConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Delete Editor REST API",
            description = "Delete Editor from the AttendX application"
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
    @DeleteMapping(path = "/editor/{email}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> deleteEditor(@PathVariable String email) {
        boolean isDeleted = editorService.deleteEditor(email);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(EditorConstants.STATUS_200, EditorConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(EditorConstants.STATUS_417, EditorConstants.MESSAGE_417_UPDATE));
        }
    }
}
