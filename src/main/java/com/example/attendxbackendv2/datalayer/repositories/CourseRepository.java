package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> , PagingAndSortingRepository<CourseEntity,Long> {
    Optional<CourseEntity> findCourseEntityByCourseCodeIgnoreCase(String courseCode);
}
