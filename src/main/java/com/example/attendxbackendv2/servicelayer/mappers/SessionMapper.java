package com.example.attendxbackendv2.servicelayer.mappers;

import com.example.attendxbackendv2.datalayer.entities.SessionEntity;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.AddressDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.SessionCardDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.SessionDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.StudentDTO;

import java.util.Map;
import java.util.stream.Collectors;

public class SessionMapper {


    public static SessionDTO mapToSessionDTO(SessionEntity sessionEntity, SessionDTO sessionDTO) {
        sessionDTO.setSessionDate(sessionEntity.getSessionDate().toString());

        Map<StudentDTO, Boolean> attendance = sessionEntity.getAttendance().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> StudentMapper.mapStudentEntityToStudentDTO(entry.getKey(), new StudentDTO(), new AddressDTO(), false),
                        Map.Entry::getValue
                ));
        sessionDTO.setSessionId(sessionEntity.getSessionId());
        return sessionDTO;
    }

    public static SessionCardDTO mapToSessionCard(SessionEntity sessionEntity, SessionCardDTO sessionCardDTO) {
        sessionCardDTO.setSessionDate(sessionEntity.getSessionDate().toString());
        sessionCardDTO.setSessionId(sessionEntity.getSessionId());
        sessionCardDTO.setCourseCode(sessionEntity.getCourse().getCourseCode());
        sessionCardDTO.setCourseName(sessionEntity.getCourse().getCourseName());
        return sessionCardDTO;
    }
}
