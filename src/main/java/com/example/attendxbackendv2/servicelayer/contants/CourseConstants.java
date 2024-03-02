package com.example.attendxbackendv2.servicelayer.contants;

import java.time.format.DateTimeFormatter;

public class CourseConstants{
    // Status codes
    public static final String STATUS_201 = "201";
    public static final String STATUS_200 = "200";
    public static final String STATUS_417 = "417";

    // Messages
    public static final String MESSAGE_201 = "Course created successfully";
    public static final String MESSAGE_200 = "Request processed successfully";
    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";
    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";

    // Date and time format
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}

