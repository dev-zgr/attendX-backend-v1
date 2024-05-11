package com.example.attendxbackendv2.presentationlayer.datatransferobjects;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SessionCardDTO extends SessionDTO{
    private String courseName;
    private String courseCode;

}
