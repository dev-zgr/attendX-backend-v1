package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import com.example.attendxbackendv2.datalayer.repositories.LecturerRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.LecturerAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.LecturerService;
import com.example.attendxbackendv2.servicelayer.mappers.CourseMapper;
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
        LecturerEntity lecturerEntity = LecturerMapper.mapLecturerDTOToLecturerEntity(new LecturerEntity(), lecturerDTO, new AddressEmbeddable());
        lecturerEntity.setRegisteredDepartment(departmentEntity);
        departmentEntity.addLecturer(lecturerEntity);
        lecturerRepository.save(lecturerEntity);
        departmentRepository.save(departmentEntity);
    }

    @Override
    @Transactional
    public List<LecturerDTO> getAllLecturers(int pageNo, boolean ascending) {
        Pageable pageable;
        if (ascending) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").descending());
        }
        List<LecturerEntity> lecturerEntities = lecturerRepository.findAll(pageable).getContent();
        return lecturerEntities.stream().map(lecturerEntity -> LecturerMapper.mapLecturerEntityToLecturerDTO(lecturerEntity, new LecturerDTO(), new AddressDTO(), false)).toList();
    }

    @Override
    @Transactional
    public LecturerDTO getLecturerByEmail(String email, boolean getDetails) throws ResourceNotFoundException {
        LecturerEntity lecturer = lecturerRepository.findLecturerEntityByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException("Lecturer", "email", email));
        LecturerDTO lecturerDTO = LecturerMapper.mapLecturerEntityToLecturerDTO(lecturer, new LecturerDTO(), new AddressDTO(), getDetails);
        List<CourseDTO> courseDTOS = lecturer.getCourses().stream().map(courseEntity -> CourseMapper.mapToCourseDTO(courseEntity, new CourseDTO(), false)).toList();
        lecturerDTO.setCourses(courseDTOS);
        return lecturerDTO;
    }

    @Override
    @Transactional
    public boolean updateLecturer(LecturerDTO lecturerDTO) throws ResourceNotFoundException {
        boolean isUpdated = false;
        lecturerRepository.findLecturerEntityByEmailIgnoreCase(lecturerDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer",
                        "email",
                        lecturerDTO.getEmail()));

        departmentRepository.findByDepartmentNameIgnoreCase(lecturerDTO.getDepartment())
                .orElseThrow(() -> new ResourceNotFoundException("Department",
                        "departmentName",
                        lecturerDTO.getDepartment()));

        // Find the lecturer
        LecturerEntity lecturerToUpdate =
                lecturerRepository.findLecturerEntityByEmailIgnoreCase(lecturerDTO.getEmail()).get();
        // Get the old department entity
        DepartmentEntity oldDepartmentEntity =
                departmentRepository.findByDepartmentNameIgnoreCase(lecturerToUpdate.getRegisteredDepartment().getDepartmentName()).get();

        // Find the new department of the Lecturer
        DepartmentEntity newDepartmentEntity =
                departmentRepository.findByDepartmentNameIgnoreCase(lecturerDTO.getDepartment()).get();

        // update other fields on Lecturer
        LecturerMapper.mapLecturerDTOToLecturerEntity(lecturerToUpdate, lecturerDTO, new AddressEmbeddable());

        // if the department has changed
        if (oldDepartmentEntity.getDepartmentId() != newDepartmentEntity.getDepartmentId()) {

            oldDepartmentEntity.removeLecturer(lecturerToUpdate);
            departmentRepository.save(oldDepartmentEntity);

            lecturerToUpdate.setRegisteredDepartment(newDepartmentEntity);
            lecturerRepository.save(lecturerToUpdate);
        }else{
            lecturerRepository.save(lecturerToUpdate);
        }

        isUpdated = true;

        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteLecturer(String email) {
        LecturerEntity lecturer = lecturerRepository.findLecturerEntityByEmailIgnoreCase(email).orElseThrow(
                () -> new ResourceNotFoundException("Lecturer", "email", email)
        );
        //break the relationship
        var department = lecturer.getRegisteredDepartment();
        department.removeLecturer(lecturer);
        departmentRepository.save(department);
        lecturer.setRegisteredDepartment(null);
        lecturerRepository.delete(lecturer);
        return true;
    }

    @Override
    public Long getPageCount() {
        return (lecturerRepository.count() + pageSize - 1) / pageSize;
    }
}
