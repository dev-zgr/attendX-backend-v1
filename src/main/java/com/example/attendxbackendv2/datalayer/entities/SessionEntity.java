package com.example.attendxbackendv2.datalayer.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@ToString
@EqualsAndHashCode
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @Column(name = "start_date", nullable = false)
    private LocalDate sessionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CourseEntity course;

    @ElementCollection
    private Map<StudentEntity, Boolean> attendance;


    public SessionEntity(LocalDate sessionDate, CourseEntity course){
        this.sessionDate = sessionDate;
        this.course = course;
        attendance = new HashMap<>();
    }


    public SessionEntity() {
        this.sessionDate = null;
        this.course = null;
        attendance = new HashMap<>();

    }
}
