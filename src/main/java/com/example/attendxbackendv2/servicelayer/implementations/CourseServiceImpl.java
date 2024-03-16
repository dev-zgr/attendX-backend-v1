package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.*;
import com.example.attendxbackendv2.datalayer.repositories.*;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.CourseAlreadyExistsException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.interfaces.CourseService;
import com.example.attendxbackendv2.servicelayer.mappers.CourseMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Value("${pagination.size}")
    private int pageSize;
    private final LecturerRepository lecturerRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public CourseServiceImpl(LecturerRepository lecturerRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository, StudentRepository studentRepository, SessionRepository sessionRepository) {
        this.lecturerRepository = lecturerRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.sessionRepository = sessionRepository;
    }



    @Override
    @Transactional
    public void createCourse(CourseDTO courseDTO) throws ResourceNotFoundException, CourseAlreadyExistsException {
        LecturerEntity lecturer= lecturerRepository.findLecturerEntityByEmailIgnoreCase(courseDTO.getLecturerEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer", "email", courseDTO.getLecturerEmail()));
        DepartmentEntity department = departmentRepository.findByDepartmentNameIgnoreCase(courseDTO.getDepartmentName())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "name", courseDTO.getDepartmentName()));
        CourseEntity courseEntity = CourseMapper.mapToCourseEntity(new CourseEntity(), courseDTO);
        courseEntity.setDepartment(department);
        department.addCourse(courseEntity);
        lecturer.addCourse(courseEntity);
        courseEntity.setLecturer(lecturer);
        List<SessionEntity> courseSessions = generateCourseSessions(courseEntity);
        courseEntity.setCourseSessions(courseSessions);
        courseRepository.save(courseEntity);
        sessionRepository.saveAll(courseSessions);
        lecturerRepository.save(lecturer);
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public List<CourseDTO> getAllCourses(int pageNo, boolean ascending) {
        Pageable pageable;
        if (ascending) {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("courseCode").ascending());
        } else {
            pageable = PageRequest.of(pageNo, pageSize, Sort.by("courseCode").descending());
        }
        List<CourseEntity> courseEntities = courseRepository.findAll(pageable).getContent();
        return courseEntities.stream().map(courseEntity -> CourseMapper.mapToCourseDTO( courseEntity, new CourseDTO(),false)).toList();

    }

    @Override
    @Transactional
    public CourseDTO getCourseByCourseCode(String courseCode, boolean getDetails) throws ResourceNotFoundException {
        CourseEntity course = courseRepository.findCourseEntityByCourseCodeIgnoreCase(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "courseCode", courseCode));
        return CourseMapper.mapToCourseDTO(course, new CourseDTO(), getDetails);
    }

    @Override
    @Transactional
    public boolean updateCourse(CourseDTO courseDTO) {
        boolean isUpdated = false;
        //First find course
        CourseEntity courseToUpdate = courseRepository.findCourseEntityByCourseCodeIgnoreCase(courseDTO.getCourseCode())
                .orElseThrow(() -> new ResourceNotFoundException("Course",
                        "courseCode",
                        courseDTO.getCourseCode()));
        // Then find department by department name if not found then throw exception
        DepartmentEntity oldDepartment = departmentRepository.findByDepartmentNameIgnoreCase(courseToUpdate.getDepartment().getDepartmentName())
                .orElseThrow(() -> new ResourceNotFoundException("Department",
                        "departmentName",
                        courseDTO.getDepartmentName()));
        // Then find lecturer by email if not found then throw exception
        LecturerEntity oldLecturer = lecturerRepository.findLecturerEntityByEmailIgnoreCase(courseToUpdate.getLecturer().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer",
                        "email",
                        courseDTO.getLecturerEmail()));
        // Then Find The students the course entity
        Set<StudentEntity> oldStudents = new HashSet<>(
                courseToUpdate.getEnrolledStudents().stream().map(student -> studentRepository.findStudentEntityByStudentId(student.getStudentId())
                        .orElseThrow(() -> new ResourceNotFoundException("Student", "email", student.getEmail()))).toList()
        );

        //First Update the changed meta fields inside the course entity not the relationships
        CourseMapper.mapToCourseEntity(courseToUpdate, courseDTO);


        DepartmentEntity newDepartment = departmentRepository.findByDepartmentNameIgnoreCase(courseDTO.getDepartmentName())
                .orElseThrow(() -> new ResourceNotFoundException("Department",
                        "departmentName",
                        courseDTO.getDepartmentName()));

        LecturerEntity newLecturer = lecturerRepository.findLecturerEntityByEmailIgnoreCase(courseDTO.getLecturerEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer",
                        "email",
                        courseDTO.getLecturerEmail()));

        Set<StudentEntity> newStudents = new HashSet<>(courseDTO.getEnrolledStudents().stream().map(student -> studentRepository.findStudentEntityByStudentId(student.getStudentNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "email", student.getEmail()))).toList());

        try{
            if(!Objects.equals(oldDepartment.getDepartmentId(), newDepartment.getDepartmentId())){
                //get rid of the old department
                oldDepartment.removeCourse(courseToUpdate);
                departmentRepository.save(oldDepartment);

                courseToUpdate.setDepartment(newDepartment);
                courseRepository.save(courseToUpdate);

                newDepartment.addCourse(courseToUpdate);
                departmentRepository.save(newDepartment);
            }
            if(!Objects.equals(oldLecturer.getUserId(), newLecturer.getUserId())){
                //get rid of the old lecturer
                oldLecturer.removeCourse(courseToUpdate);
                lecturerRepository.save(oldLecturer);

                courseToUpdate.setLecturer(newLecturer);
                courseRepository.save(courseToUpdate);

                newLecturer.addCourse(courseToUpdate);
                lecturerRepository.save(newLecturer);
            }

            Set<StudentEntity> studentsToRemove = new HashSet<>(oldStudents);
            studentsToRemove.removeAll(newStudents);

            Set<StudentEntity> studentsToAdd = new HashSet<>(newStudents);
            studentsToAdd.removeAll(oldStudents);

            for (StudentEntity student : studentsToRemove) {
                //First get rid of the student
                student.unrollFromCourse(courseToUpdate);
                studentRepository.save(student);

                courseToUpdate.unrollStudent(student);
                courseRepository.save(courseToUpdate);
            }

            for (StudentEntity student : studentsToAdd) {
                courseToUpdate.enrollStudent(student);
                courseRepository.save(courseToUpdate);
                student.enrollToCourse(courseToUpdate);
                studentRepository.save(student);
            }

        }finally {
            courseRepository.save(courseToUpdate);

        }

        isUpdated = true;
        return isUpdated;
    }

    private List<SessionEntity> generateCourseSessions(CourseEntity course){
        List<SessionEntity> sessions = new ArrayList<>();
        LocalDate currentDate = course.getStartDate();
        while (currentDate.isBefore(course.getEndDate())){
            SessionEntity session = new SessionEntity();
            session.setSessionDate(currentDate);
            session.setCourse(course);
            sessions.add(session);
            currentDate = currentDate.plusDays(7);
        }
        return sessions;
    }
}
