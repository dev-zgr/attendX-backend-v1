package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import com.example.attendxbackendv2.datalayer.entities.LecturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository  extends JpaRepository<LecturerEntity,Long>, PagingAndSortingRepository<LecturerEntity, Long> {
    Optional<LecturerEntity> findLecturerEntityByEmailIgnoreCase(String email);
}
