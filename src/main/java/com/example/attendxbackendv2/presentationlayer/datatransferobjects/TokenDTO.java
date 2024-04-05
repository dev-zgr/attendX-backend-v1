package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "LoginDTO",
        description = "Schema to hold login information"
)
@Data
@NoArgsConstructor
public class TokenDTO {
    private String token;
    private UserBaseDTO user;

    public TokenDTO(String token, UserBaseDTO user) {
        this.token = token;
        this.user = user;
    }
}
