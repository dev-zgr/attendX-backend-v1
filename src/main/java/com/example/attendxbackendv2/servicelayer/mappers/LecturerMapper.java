package com.example.attendxbackendv2.servicelayer.mappers;


import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;

public class LecturerMapper {

    public static LecturerEntity mapLecturerDTOToLecturerEntity(LecturerEntity lecturerEntity, LecturerDTO lecturerDTO, AddressEmbeddable addressEmbeddable) {
        lecturerEntity.setFirstName(lecturerDTO.getFirstName());
        lecturerEntity.setLastName(lecturerDTO.getLastName());
        lecturerEntity.setEmail(lecturerDTO.getEmail());
        lecturerEntity.setPassword(lecturerDTO.getPassword());
        lecturerEntity.setPhoneNumber(lecturerDTO.getPhoneNumber());
        lecturerEntity.setAddress(AddressMapper.mapAddressDTOToAddressEmbeddable(addressEmbeddable, lecturerDTO.getAddress()));
        return lecturerEntity;
    }

    public static LecturerDTO mapLecturerEntityToLecturerDTO(LecturerEntity lecturerEntity, LecturerDTO lecturerDTO, AddressDTO addressDTO) {
        lecturerDTO.setFirstName(lecturerEntity.getFirstName());
        lecturerDTO.setLastName(lecturerEntity.getLastName());
        lecturerDTO.setEmail(lecturerEntity.getEmail());
        lecturerDTO.setPassword(lecturerEntity.getPassword());
        lecturerDTO.setPhoneNumber(lecturerEntity.getPhoneNumber());
        lecturerDTO.setAddress(AddressMapper.mapAddressEmbeddableToAddressDTO(lecturerEntity.getAddress(), addressDTO));
        return lecturerDTO;
    }
}
