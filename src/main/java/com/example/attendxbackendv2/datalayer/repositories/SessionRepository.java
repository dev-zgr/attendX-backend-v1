package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.CourseEntity;
import com.example.attendxbackendv2.datalayer.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findByCourse(CourseEntity course);
}
