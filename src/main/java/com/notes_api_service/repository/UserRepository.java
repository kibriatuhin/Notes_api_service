package com.notes_api_service.repository;

import com.notes_api_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByEmail(String email);
}
