package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ErrorResponseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.ResponseDTO;
import com.example.attendxbackendv2.servicelayer.contants.CourseConstants;
import com.example.attendxbackendv2.servicelayer.contants.SessionConstants;
import com.example.attendxbackendv2.servicelayer.interfaces.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Session",
        description = "Session API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
@CrossOrigin(origins = "${attendx.crossorigin.url}")
public class SessionController {


    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Operation(
            summary = "Attend Student Course Session REST API",
            description = "Attend Student Course Session REST API"
    )
    @ApiResponses({
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
                    responseCode = "404",
                    description = "HTTP Status Not Found it may be causing due to trying to access non-existing course session" +
                            "or it may be cause due to trying to access non-existing student id",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request this may be cause due to invalid request parameters eg." +
                            "student id that isn't registered in the system or session id that isn't registered in the system",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PatchMapping(path = "/session",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> attendToSession(
            @RequestParam(name = "session-id", defaultValue = "null") Long sessionId,
            @RequestParam(name = "student-id", defaultValue = "null") String userId) {

        boolean isAttended = sessionService.attendToSession(sessionId, userId);

        if (isAttended) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED).body(new ResponseDTO(
                            SessionConstants.STATUS_202, CourseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDTO(
                            SessionConstants.STATUS_417, CourseConstants.MESSAGE_417_UPDATE)
            );
        }
    }


    @Operation(
            summary = "Starts Session REST API",
            description = "Starts the session with given ID  REST API"
    )
    @ApiResponses({
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
                    responseCode = "404",
                    description = "HTTP Status Not Found it may be causing due to trying to access non-existing course session" +
                            "or it may be cause due to trying to access non-existing student id",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status Bad Request this may be cause due to try to start the session with expired date",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PutMapping(path = "/session",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> startSession(
            @RequestParam(name = "session-id", defaultValue = "null") Long sessionId) {
        boolean isStarted = sessionService.startSession(sessionId);

        if (isStarted) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED).body(new ResponseDTO(
                            SessionConstants.STATUS_202, CourseConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(
                    new ResponseDTO(
                            SessionConstants.STATUS_417, CourseConstants.MESSAGE_417_UPDATE)
            );
        }
    }



    @GetMapping(path = "/session/{sessionId}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getAttendanceReport(
            @PathVariable Long sessionId) {

        byte[] file = sessionService.getAttendanceReport(sessionId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "attendance.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(file);
    }
}
