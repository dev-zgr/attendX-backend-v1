package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.UserBaseEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.UserBaseDTO;

public class UserGenericMapper {

    public static <T extends UserBaseEntity> T mapUserDTOToUserEntity(T userEntity, UserBaseDTO userDTO, AddressEmbeddable addressEmbeddable) {
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userEntity.setPhoneNumber(userDTO.getPhoneNumber());
        userEntity.setAddress(AddressMapper.mapAddressDTOToAddressEmbeddable(addressEmbeddable, userDTO.getAddress()));
        return userEntity;
    }

    public static <T extends UserBaseDTO> T mapUserEntityToUserDTO(UserBaseEntity userBaseEntity, T userBaseDTO, AddressDTO addressDTO, boolean getDetails) {
        userBaseDTO.setFirstName(userBaseEntity.getFirstName());
        userBaseDTO.setLastName(userBaseEntity.getLastName());
        userBaseDTO.setEmail(userBaseEntity.getEmail());

        if (getDetails) {
            userBaseDTO.setPhoneNumber(userBaseEntity.getPhoneNumber());
            userBaseDTO.setPassword(userBaseEntity.getPassword());
            userBaseDTO.setAddress(AddressMapper.mapAddressEmbeddableToAddressDTO(userBaseEntity.getAddress(), addressDTO));
            userBaseDTO.setRole(userBaseEntity.getUserType());
        }

        return userBaseDTO;
    }


}
