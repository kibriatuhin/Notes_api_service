package com.notes_api_service.repository;

import com.notes_api_service.entity.FavouriteNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteNotesRepository extends JpaRepository<FavouriteNote,Integer> {
    List<FavouriteNote> findByUserId(Integer userId);
}
