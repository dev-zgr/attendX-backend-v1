package com.example.attendxbackendv2.ai.serviceregistiration;

import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.datalayer.repositories.DepartmentRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.CourseDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.DepartmentDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.LecturerDTO;
import com.example.attendxbackendv2.servicelayer.mappers.CourseMapper;
import com.example.attendxbackendv2.servicelayer.mappers.DepartmentMapper;
import com.example.attendxbackendv2.servicelayer.mappers.LecturerMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;


@Component
public class CourseService implements Function<Request, Response> {

    private final DepartmentRepository departmentRepository;

    public CourseService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }




    @Override
    public Response apply(Request request) {
        List<DepartmentEntity> departmentEntityList = departmentRepository.findAll();
        List<DepartmentDTO> departmentDTOS = departmentEntityList
                .stream()
                .map((departmentEntity -> {
                    DepartmentDTO departmentDTO = DepartmentMapper.mapToDepartmentDTO(departmentEntity,new DepartmentDTO());
                    departmentDTO.setLecturers(
                            departmentEntity.getRegisteredLecturers().stream().map(
                                    lecturerEntity -> LecturerMapper.mapLecturerEntityToLecturerDTO(lecturerEntity, new LecturerDTO(), new AddressDTO(), false)
                            ).toList()
                    );
                    departmentDTO.setOfferedCourses(
                            departmentEntity.getCourses().stream().map(
                                    courseEntity -> CourseMapper.mapToCourseDTO(courseEntity, new CourseDTO(),false)
                            ).toList()
                    );
                    return departmentDTO;
                })).toList();
        return new Response(departmentDTOS);
    }
}