package com.notes_api_service.repository;

import com.notes_api_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByIdAndIsDeletedFalse(Integer name);
    List<Category> findByIsDeletedFalse();
    List<Category> findByIsActiveTrueAndIsDeletedFalse();
}
