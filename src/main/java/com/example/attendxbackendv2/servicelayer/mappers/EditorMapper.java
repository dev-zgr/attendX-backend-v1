package com.example.attendxbackendv2.servicelayer.mappers;

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
        return UserGenericMapper.mapUserEntityToUserDTO(editorEntity, editorDTO, addressDTO, getDetails);
    }
}

