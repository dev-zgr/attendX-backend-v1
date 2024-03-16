package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Schema(
        name = "EditorDTO",
        description = "Schema to hold Editor information"
)
@Data
@NoArgsConstructor
public class EditorDTO extends UserBaseDTO {

    public EditorDTO(String firstName, String lastName, String email, String password, String phoneNumber, AddressDTO address,String role) {
        super(firstName, lastName, email, password, phoneNumber, address,role);
    }

}

