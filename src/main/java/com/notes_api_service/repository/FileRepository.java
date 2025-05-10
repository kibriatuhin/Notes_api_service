package com.notes_api_service.repository;

import com.notes_api_service.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {

}
