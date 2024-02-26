package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import com.example.attendxbackendv2.datalayer.repositories.LecturerRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.LecturerAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.LecturerService;
import com.example.attendxbackendv2.servicelayer.mappers.AddressMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepository lecturerRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public LecturerServiceImpl(LecturerRepository lecturerRepository, DepartmentRepository departmentRepository) {
        this.lecturerRepository = lecturerRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public void createLecturer(LecturerDTO lecturerDTO) throws LecturerAlreadyExistException, ResourceNotFoundException {
        // first try to find lecturer by email if found then throw exception
        if (lecturerRepository.findLecturerEntityByEmailIgnoreCase(lecturerDTO.getEmail()).isPresent()) {
            throw new LecturerAlreadyExistException("Lecturer already exist with email: " + lecturerDTO.getEmail());
        }
        // Then try to find department by department name if not found then throw exception
        if (departmentRepository.findByDepartmentNameIgnoreCase(lecturerDTO.getDepartment()).isEmpty()) {
            throw new ResourceNotFoundException("Department", "Department Name", lecturerDTO.getDepartment());
        }

        DepartmentEntity departmentEntity = departmentRepository.findByDepartmentNameIgnoreCase(lecturerDTO.getDepartment()).get();
        LecturerEntity lecturerEntity = new LecturerEntity(lecturerDTO.getFirstName(),
                lecturerDTO.getLastName(),
                lecturerDTO.getEmail(),
                lecturerDTO.getPhoneNumber(),
                lecturerDTO.getPassword(),
                AddressMapper.mapAddressDTOToAddressEmbeddable(new AddressEmbeddable(),lecturerDTO.getAddress()),
                departmentEntity);
        departmentRepository.save(departmentEntity);
        lecturerRepository.save(lecturerEntity);
        departmentEntity.addLecturer(lecturerEntity);
        lecturerEntity.setRegisteredDepartment(departmentEntity);
    }
}
