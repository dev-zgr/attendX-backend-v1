package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import com.example.attendxbackendv2.datalayer.repositories.LecturerRepository;
import com.example.attendxbackendv2.interfaces.SelectableInterface;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.OptionDTO;
import com.example.attendxbackendv2.servicelayer.contants.OptionCodes;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.OptionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OptionServiceImpl implements OptionService {


    private final DepartmentRepository departmentRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public OptionServiceImpl(DepartmentRepository departmentRepository, LecturerRepository lecturerRepository) {
        this.departmentRepository = departmentRepository;
        this.lecturerRepository = lecturerRepository;
    }


    @Override
    @Transactional
    public List<OptionDTO> getOptions(OptionCodes optionCode) {

        return switch (optionCode) {
            case DEPT_ALL -> departmentRepository
                    .findAll(Sort.by("departmentName"))
                    .stream()
                    .map(departmentEntity -> (SelectableInterface) departmentEntity)
                    .map(selectable -> new OptionDTO(selectable.getIdentifier(), selectable.getLabel()))
                    .collect(Collectors.toList());
            case LECT_ALL -> lecturerRepository
                    .findAll(Sort.by("firstName"))
                    .stream()
                    .map(lecturerEntity -> (SelectableInterface) lecturerEntity)
                    .map(selectable -> new OptionDTO(selectable.getIdentifier(), selectable.getLabel()))
                    .collect(Collectors.toList());
            default -> throw new ResourceNotFoundException("Option", "Option_Code", optionCode.toString());
        };


    }
}
