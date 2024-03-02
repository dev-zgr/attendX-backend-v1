package com.example.attendxbackendv2.datalayer.repositories;

import com.example.attendxbackendv2.datalayer.entities.EditorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface EditorRepository extends JpaRepository<EditorEntity, Long>, PagingAndSortingRepository<EditorEntity,Long> {
    Optional<EditorEntity> findEditorEntitiesByEmailIgnoreCase(String email);


}