package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import com.example.attendxbackendv2.datalayer.repositories.LecturerRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.LecturerAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.LecturerService;
import com.example.attendxbackendv2.servicelayer.mappers.AddressMapper;
import com.example.attendxbackendv2.servicelayer.mappers.DepartmentMapper;
import com.example.attendxbackendv2.servicelayer.mappers.LecturerMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LecturerServiceImpl implements LecturerService {
    @Value("${pagination.size}")
    private int pageSize;
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
        LecturerEntity lecturerEntity = LecturerMapper.mapLecturerDTOToLecturerEntity(new LecturerEntity(), lecturerDTO , new AddressEmbeddable());
        lecturerEntity.setRegisteredDepartment(departmentEntity);
        departmentEntity.addLecturer(lecturerEntity);
        lecturerRepository.save(lecturerEntity);
        departmentRepository.save(departmentEntity);
    }

    @Override
    public List<LecturerDTO> getAllLecturers(int pageNo, boolean ascending) {
        Pageable pageable;
        if (ascending) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").descending());
        }
        List<LecturerEntity> lecturerEntities =  lecturerRepository.findAll(pageable).getContent();
        return lecturerEntities.stream()
                .map(lecturerEntity -> LecturerMapper
                        .mapLecturerEntityToLecturerDTO(lecturerEntity,
                                new LecturerDTO(),
                                new AddressDTO(),
                                true)).toList();
    }
}
