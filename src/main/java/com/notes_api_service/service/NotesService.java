package com.notes_api_service.service;

import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
     Boolean saveNotes(String notes, MultipartFile file) throws Exception;

     byte[] downloadFile(FileDetails fileDetails) throws Exception;
     FileDetails getFileDetails(Integer id) throws Exception;


     List<NotesDto> getAllNotes();
     //NotesDto getNotes(Integer id);

}
