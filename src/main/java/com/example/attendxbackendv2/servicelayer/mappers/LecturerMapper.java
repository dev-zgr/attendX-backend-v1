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

    public static LecturerDTO mapLecturerEntityToLecturerDTO(LecturerEntity lecturerEntity, LecturerDTO lecturerDTO, AddressDTO addressDTO, boolean getDetails) {

        lecturerDTO.setFirstName(lecturerEntity.getFirstName());
        lecturerDTO.setLastName(lecturerEntity.getLastName());
        lecturerDTO.setDepartment(lecturerEntity.getRegisteredDepartment().getDepartmentName());
        lecturerDTO.setEmail(lecturerEntity.getEmail());

        if (getDetails) {
            lecturerDTO.setPhoneNumber(lecturerEntity.getPhoneNumber());
            lecturerDTO.setPassword(lecturerEntity.getPassword());
            lecturerDTO.setAddress(AddressMapper.mapAddressEmbeddableToAddressDTO(lecturerEntity.getAddress(), addressDTO));
        }
        return lecturerDTO;
    }
}
