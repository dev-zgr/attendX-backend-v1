package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.OptionDTO;
import com.example.attendxbackendv2.servicelayer.contants.OptionCodes;

import java.util.List;

public interface OptionService {
    /**
     * This method is used to fetch all the options from the database
     * @param optionCode code of option to be fetched
     * @return List of options
     */
    List<OptionDTO> getOptions(OptionCodes optionCode);
}
