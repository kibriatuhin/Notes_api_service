package com.notes_api_service.repository;

import com.notes_api_service.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface NotesRepository extends JpaRepository<Notes, Integer> {

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer id, Pageable pageable);
    List<Notes> findByCreatedByAndIsDeletedTrue(Integer id);
    List<Notes> findAllByIsDeletedAndDeletedOnBefore(Boolean isDeleted, LocalDateTime localDateTime);
    @Query("select n from Notes n where (lower(n.title) like lower(concat('%', :keyword, '%')) " +
            " or lower(n.description) like lower(concat('%', :keyword, '%')) " +
            " or lower(coalesce(n.category.name, '')) like lower(concat('%', :keyword, '%'))) " +
            " and n.isDeleted = false " +
            " and n.createdBy = :uId")
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("uId") Integer uId, Pageable pageable);
}
