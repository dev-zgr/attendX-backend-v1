package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.AddressEmbeddable;
import com.example.attendxbackendv2.datalayer.entities.StudentEntity;
import com.example.attendxbackendv2.datalayer.repositories.StudentRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.exceptions.StudentAlreadyExistException;
import com.example.attendxbackendv2.servicelayer.interfaces.StudentService;
import com.example.attendxbackendv2.servicelayer.mappers.StudentMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Value("${pagination.size}")
    private int pageSize;

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void createStudent(StudentDTO studentDTO) throws StudentAlreadyExistException, ResourceNotFoundException {
        // Check if student already exists with email
        if (studentRepository.findStudentEntityByEmailIgnoreCase(studentDTO.getEmail()).isPresent()) {
            throw new StudentAlreadyExistException("Student already exists with email: " + studentDTO.getEmail());
        }
        StudentEntity studentEntity = StudentMapper.mapStudentDTOToStudentEntity(new StudentEntity(), studentDTO, new AddressEmbeddable());
        studentRepository.save(studentEntity);
    }

    @Override
    @Transactional
    public List<StudentDTO> getAllStudents(int pageNo, boolean ascending) {
        Pageable pageable;
        if (ascending) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("firstName").descending());
        }
        List<StudentEntity> studentEntities = studentRepository.findAll(pageable).getContent();
        return studentEntities.stream().map(studentEntity -> StudentMapper.mapStudentEntityToStudentDTO(studentEntity, new StudentDTO(), new AddressDTO(), false)).toList();
    }

    @Override
    @Transactional
    public StudentDTO getStudentByEmail(String email, boolean getDetails) throws ResourceNotFoundException {
        StudentEntity student = studentRepository.findStudentEntityByEmailIgnoreCase(email).orElseThrow(() -> new ResourceNotFoundException("Student", "email", email));
        return StudentMapper.mapStudentEntityToStudentDTO(student, new StudentDTO(), new AddressDTO(), getDetails);
    }

    @Override
    @Transactional
    public boolean updateStudent(StudentDTO studentDTO) throws ResourceNotFoundException {
        boolean isUpdated = false;
        StudentEntity studentToUpdate = studentRepository.findStudentEntityByEmailIgnoreCase(studentDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Student",
                        "email",
                        studentDTO.getEmail()));
        StudentMapper.mapStudentDTOToStudentEntity(studentToUpdate, studentDTO, new AddressEmbeddable());
        studentRepository.save(studentToUpdate);
        isUpdated = true;
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteStudent(String email) {
        StudentEntity student = studentRepository.findStudentEntityByEmailIgnoreCase(email).orElseThrow(
                () -> new ResourceNotFoundException("Student", "email", email)
        );
        studentRepository.delete(student);
        return true;
    }


}
