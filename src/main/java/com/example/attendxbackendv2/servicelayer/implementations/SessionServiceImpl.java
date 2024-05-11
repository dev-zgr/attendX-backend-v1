package com.example.attendxbackendv2.servicelayer.implementations;

import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import com.example.attendxbackendv2.datalayer.entities.SessionEntity;
import com.example.attendxbackendv2.datalayer.entities.StudentEntity;
import com.example.attendxbackendv2.datalayer.repositories.LecturerRepository;
import com.example.attendxbackendv2.datalayer.repositories.SessionRepository;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.SessionCardDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.UserBaseDTO;
import com.example.attendxbackendv2.servicelayer.exceptions.NotRelatedException;
import com.example.attendxbackendv2.servicelayer.exceptions.ResourceNotFoundException;
import com.example.attendxbackendv2.servicelayer.exceptions.SessionExpiredException;
import com.example.attendxbackendv2.servicelayer.interfaces.LoginService;
import com.example.attendxbackendv2.servicelayer.interfaces.SessionService;
import com.example.attendxbackendv2.servicelayer.mappers.SessionMapper;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final LecturerRepository lecturerRepository;
    private final LoginService loginService;

    @Autowired
    public SessionServiceImpl(SessionRepository sessionRepository, LecturerRepository userRepository1, LoginService loginService) {
        this.sessionRepository = sessionRepository;
        this.lecturerRepository = userRepository1;
        this.loginService = loginService;
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

    @Override
    public byte[] getAttendanceReport(Long sessionId) {
        SessionEntity sessionEntity = sessionRepository.findById(sessionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Session", "sessionId", sessionId.toString())
                );

        byte[] fileContent = null;
        try {
            // Writing to CSV file
            ICSVWriter writer = new CSVWriterBuilder(new FileWriter("yourfile.csv"))
                    .withSeparator(',')
                    .build();
            String[] headers = "Number#First Name#Last Name#Attended".split("#");
            writer.writeNext(headers);

            sessionEntity.getAttendance().forEach((student, isAttended) -> {
                String[] entry = String.format("%s#%s#%s#%s\n", student.getStudentId(), student.getFirstName(), student.getLastName(), isAttended).split("#");
                writer.writeNext(entry);
            });

            writer.close();
            File file = new File("yourfile.csv");
            FileInputStream fis = new FileInputStream(file);
            fileContent = new byte[(int) file.length()];
            fileContent = fis.readAllBytes();
            fis.close();
        } catch (Exception e) {
            return new byte[6];
        }

        return fileContent;
    }

    @Override
    @Transactional
    public Map<String, List<SessionCardDTO>> getUpcomingSessionByToken(String token) {
        Map<String, List<SessionCardDTO>> upcomingSessions = new HashMap<>();
        upcomingSessions.put("todaySessions", new ArrayList<>());
        upcomingSessions.put("nextWeekSessions", new ArrayList<>());
        String loginRole = loginService.validateToken(token);
        if (loginRole.equalsIgnoreCase("LECTURER")) {
            UserBaseDTO userBaseEntity = loginService.getUserByToken(UUID.fromString(token));
            LecturerEntity lecturer = lecturerRepository.findLecturerEntityByEmailIgnoreCase(userBaseEntity.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Lecturer", "email", userBaseEntity.getEmail()));
            List<SessionEntity> allSessions = lecturer.getCourses().stream()
                    .flatMap(course -> course.getCourseSessions().stream())
                    .toList();
            allSessions.forEach(session -> {
                        LocalDate today = LocalDate.now();
                        LocalDate nextWeek = today.plusWeeks(1);
                        LocalDate sessionDate = session.getSessionDate();
                        if(sessionDate.isEqual(today) && session.getAttendance().isEmpty()){
                            upcomingSessions.get("todaySessions").add(SessionMapper.mapToSessionCard(session, new SessionCardDTO()));
                        }else if(sessionDate.isAfter(today) && sessionDate.isBefore(nextWeek) && session.getAttendance().isEmpty()){
                            upcomingSessions.get("nextWeekSessions").add(SessionMapper.mapToSessionCard(session, new SessionCardDTO()));
                        }
                    });
        }
        return upcomingSessions;
    }


}

