package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface Department repository.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>, PagingAndSortingRepository<DepartmentEntity, Long> {
    Optional<DepartmentEntity> findByDepartmentNameIgnoreCase(String departmentName);
}

