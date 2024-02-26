package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;

public class AddressMapper {
    public static AddressEmbeddable mapAddressDTOToAddressEmbeddable(AddressEmbeddable addressEmbeddable, AddressDTO addressDTO) {
        addressEmbeddable.setStreetFirstLine(addressDTO.getStreetFirstLine());
        addressEmbeddable.setStreetSecondLine(addressDTO.getStreetSecondLine());
        addressEmbeddable.setCity(addressDTO.getCity());
        addressEmbeddable.setState(addressDTO.getState());
        addressEmbeddable.setCountry(addressDTO.getCountry());
        addressEmbeddable.setZipCode(addressDTO.getZipCode());
        return addressEmbeddable;
    }

    public static AddressDTO mapAddressEmbeddableToAddressDTO(AddressEmbeddable addressEmbeddable, AddressDTO addressDTO){
        addressDTO.setStreetFirstLine(addressEmbeddable.getStreetFirstLine());
        addressDTO.setStreetSecondLine(addressEmbeddable.getStreetSecondLine());
        addressDTO.setCity(addressEmbeddable.getCity());
        addressDTO.setCountry(addressEmbeddable.getCountry());
        addressDTO.setZipCode(addressEmbeddable.getZipCode());
        return addressDTO;
    }
}
