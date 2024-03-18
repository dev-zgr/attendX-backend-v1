package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.SessionEntity;
import com.example.attendxbackendv2.datalayer.entities.StudentEntity;
import com.example.attendxbackendv2.datalayer.repositories.CourseRepository;
import com.example.attendxbackendv2.datalayer.repositories.SessionRepository;
import com.example.attendxbackendv2.datalayer.repositories.StudentRepository;
import com.example.attendxbackendv2.servicelayer.exceptions.NotRelatedException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.exceptions.SessionExpiredException;
import com.example.attendxbackendv2.servicelayer.interfaces.SessionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }


    @Override
    public boolean attendToSession(Long sessionId, String studentID) {
        boolean isAttended = false;
        SessionEntity sessionEntity = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Session", "sessionId", sessionId.toString())
                );
        StudentEntity studentEntity = sessionEntity.getAttendance().keySet().stream()
                .filter(student -> student.getStudentId().equalsIgnoreCase(studentID))
                .findFirst().orElseThrow(() ->
                        new NotRelatedException("Student", "studentId", studentID, "Session")
                );
        sessionEntity.getAttendance().put(studentEntity, true);
        sessionRepository.save(sessionEntity);
        isAttended = true;
        return isAttended;
    }

    @Override
    @Transactional
    public boolean startSession(Long sessionId) {
        boolean isStarted = false;
        SessionEntity sessionEntity = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Session", "sessionId", sessionId.toString())
                );
        var currentDate = LocalDate.now();
        var sessionDate = sessionEntity.getSessionDate();
        if (currentDate.isAfter(sessionDate)) {

            throw new SessionExpiredException(sessionId.toString());
        }
        sessionEntity.startSession();
        sessionRepository.save(sessionEntity);
        isStarted = true;
        return isStarted;
    }
}
