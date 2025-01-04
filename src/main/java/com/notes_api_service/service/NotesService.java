package com.notes_api_service.service;

import com.notes_api_service.dto.NotesDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
     Boolean saveNotes(String notes, MultipartFile file) throws Exception;


     List<NotesDto> getAllNotes();
     //NotesDto getNotes(Integer id);

}
