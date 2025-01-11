package com.notes_api_service.repository;

import com.notes_api_service.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer id, Pageable pageable);
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);
    List<Notes> findAllByIsDeletedAndDeletedOnBefore(Boolean isDeleted, LocalDateTime localDateTime);
}
