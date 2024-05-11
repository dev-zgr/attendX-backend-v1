package com.example.attendxbackendv2.servicelayer.interfaces;

import com.example.attendxbackendv2.presentationlayer.datatransferobjects.SessionCardDTO;
import com.example.attendxbackendv2.presentationlayer.datatransferobjects.SessionDTO;

import java.util.List;
import java.util.Map;

public interface SessionService {

    /**
     * Allows a student to attend a session identified by its session ID.
     *
     * @param sessionId The ID of the session the student is attending.
     * @param studentID The ID of the student attending the session.
     * @return true if the student successfully attends the session, false otherwise.
     */
    boolean attendToSession(Long sessionId, String studentID);

    /**
     * Initiates a session with the specified session ID.
     *
     * @param sessionId The ID of the session to be started.
     * @return true if the session is successfully started, false otherwise.
     */
    boolean startSession(Long sessionId);

    /**
     * Prepares the attandance report and returns the attendance report as a byte array.
     * @param sessionId requested sessions ID
     * @return byte array of the attendance report
     */
    byte[] getAttendanceReport(Long sessionId);

    /**
     * This method lists the upcoming sessions for a student identified by their token.
     *
     * @param token The token of the student.
     * @return A list of upcoming sessions for the student.
     */
    Map<String, List<SessionCardDTO>> getUpcomingSessionByToken(String token);
}
