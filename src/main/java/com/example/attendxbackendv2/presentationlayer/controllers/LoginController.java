package com.example.attendxbackendv2.presentationlayer.controllers;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.*;
import com.example.attendxbackendv2.servicelayer.contants.LoginConstants;
import com.example.attendxbackendv2.servicelayer.exceptions.InvalidCredentialsException;
import com.example.attendxbackendv2.servicelayer.interfaces.LoginService;
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

import java.util.UUID;

@Tag(
        name = "Login",
        description = "Login API endpoints")
@RestController
@RequestMapping(value = "/api/v1", produces = "application/json")
@Validated
@CrossOrigin(origins = "${attendx.crossorigin.url}")
public class LoginController {

    private final LoginService loginService;


    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
        try {
            UUID token = loginService.login(loginDTO.getEmail(), loginDTO.getPassword());
            UserBaseDTO user = loginService.getUserByToken(token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new TokenDTO(token.toString(), user));

        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @Operation(
            summary = "logs out the user with given token",
            description = "Controller for logging out the user with the token"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "202",
                            description = "HTTP Status Accepted, user successfully logged out",
                            content = @Content(
                                    schema = @Schema(implementation = ResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "HTTP Status Not Found it may be causing due to trying to log out using the old session token",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "HTTP Status Expectation Failed this response may occur due to anomalies in system",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
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
    @DeleteMapping(value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO> logout(@RequestParam(name = "token") String token) {
        boolean isLoggedOut = loginService.logout(token);
        if (isLoggedOut) {
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(new ResponseDTO(LoginConstants.STATUS_202, LoginConstants.STATUS_202));
        } else {
            return  ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDTO(LoginConstants.STATUS_417, LoginConstants.MESSAGE_417_LOGOUT));

        }
    }

    @GetMapping(path = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getUserDetailsByToken(@RequestParam(name = "token") UUID token) {
        try {
            UserBaseDTO user = loginService.getUserByToken(token);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(user);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
