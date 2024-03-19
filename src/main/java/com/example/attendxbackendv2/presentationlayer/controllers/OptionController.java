package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.OptionDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;
import com.example.attendxbackendv2.servicelayer.contants.OptionCodes;
import com.example.attendxbackendv2.servicelayer.interfaces.OptionService;
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
        name = "Option API endpoints",
        description = "This endpoint used to fetch options")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
@CrossOrigin(origins = "${attendx.crossorigin.url}")
public class OptionController {

    private final OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService){
        this.optionService = optionService;
    }

    @Operation(
            summary = "Fetch The options from the AttendX application",
            description = "Fetch all option details from the attendX application" +
                    "This will be mainly used to show multi value select box items"
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
    @GetMapping(value = "/option", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<OptionDTO>> getOptions(
            @RequestParam(name = "option-code", defaultValue = "null") OptionCodes optionCode
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(optionService.getOptions(optionCode));
    }

}
