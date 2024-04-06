package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.CourseEntity;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> , PagingAndSortingRepository<CourseEntity,Long> {
    Optional<CourseEntity> findCourseEntityByCourseCodeIgnoreCase(String courseCode);
    Page<CourseEntity> findAllByLecturer(Pageable pageable, LecturerEntity lecturer);
}
