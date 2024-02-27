package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.servicelayer.contants.DepartmentConstants;
import com.example.attendxbackendv2.servicelayer.interfaces.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CRUD REST APIs for Departments",
        description = "Provides a fully functional CRUD REST API for Departments in the attendX application."
)
@RestController
@RequestMapping(path = "/api/v1",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @Operation(
            summary = "Create Department REST API",
            description = "Create a new department in the attendX application"
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
    }
    )
    @PostMapping(path = "/department", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        departmentService.createDepartment(departmentDTO);
        return ResponseEntity.status(201).body(new ResponseDTO(
                Integer.toString(HttpStatus.CREATED.value()),
                "Department created successfully"));
    }

    @Operation(
            summary = "Fetch Department REST API",
            description = "Fetch department details from the attendX application"
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
                    )
            })
    @GetMapping(path = "/department/{departmentName}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String departmentName, @RequestParam boolean fetchDetails) {
        return ResponseEntity.ok(departmentService.fetchDepartmentDetailsByDepartmentName(departmentName, fetchDetails));
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
                                    schema = @Schema(implementation = DepartmentDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "HTTP Status Internal Server Error",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @GetMapping(path = "/department",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(
            @RequestParam(name = "page-no", defaultValue = "0") int pageNo,
            @RequestParam(name = "ascending", defaultValue = "false") boolean ascending,
            @RequestParam(name = "get-details", defaultValue = "false") boolean getDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(departmentService.getAllDepartments(pageNo, ascending));
    }

    @Operation(
            summary = "Update Department REST API",
            description = "Update department details in the attendX application"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "202",
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
                            description = "HTTP Status Bad Request it may be causing due to invalid input or department not found",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @PutMapping(path = "/department",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> updateDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        boolean isUpdated = departmentService.updateDepartmentId(departmentDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_200, DepartmentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_417, DepartmentConstants.MESSAGE_417_UPDATE));
        }
    }


    @Operation(
            summary = "Delete Department REST API",
            description = "Delete department from the attendX application"
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
                    )
            }
    )
    @DeleteMapping(path = "/department",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> deleteDepartment(@RequestParam(name = "department_name", defaultValue = "null") @Size(min = 8, max = 60) String departmentName) {
        boolean isDeleted = departmentService.deleteDepartment(departmentName);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_200, DepartmentConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(DepartmentConstants.STATUS_417, DepartmentConstants.MESSAGE_417_UPDATE));
        }
    }


}
