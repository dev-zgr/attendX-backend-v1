package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.config.UserConfigConstants;
import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.EditorEntity;
import com.example.attendxbackendv2.datalayer.entities.UserBaseEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.EditorDTO;

public class EditorMapper {

    public static EditorEntity mapEditorDTOToUserBaseEntity(EditorEntity editorEntity, EditorDTO editorDTO, AddressEmbeddable addressEmbeddable) {
        return UserGenericMapper.mapUserDTOToUserEntity(editorEntity, editorDTO, addressEmbeddable);
    }

    public static EditorDTO mapUserBaseEntityToEditorDTO(EditorEntity editorEntity, EditorDTO editorDTO, AddressDTO addressDTO, boolean getDetails) {
        EditorDTO editorDTO1 =  UserGenericMapper.mapUserEntityToUserDTO(editorEntity, editorDTO, addressDTO, getDetails);
        editorDTO1.setRole(UserConfigConstants.EDITOR_ROLE_VALUE);
        return editorDTO1;
    }
}

