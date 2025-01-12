package com.notes_api_service.service;

import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FavouriteNote;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NotesService {
     Boolean saveNotes(String notes, MultipartFile file) throws Exception;

     byte[] downloadFile(FileDetails fileDetails) throws Exception;
     FileDetails getFileDetails(Integer id) throws Exception;

     NotesResponse getAllNotesByUser(Integer id,Integer pageNo , Integer pageSize) ;

     void softDeleteNotesById(Integer id) throws Exception;
     void hardDeleteNotesById(Integer id) throws Exception;

     void restoreNotesById(Integer id) throws Exception;


     List<NotesDto> getAllNotes();

     List<NotesDto> getUserRecycleBinNotes(Integer userId);

     void emptyRecycleByUser(Integer userId);
     //NotesDto getNotes(Integer id);
     void favouriteNotes(Integer noteId) throws Exception;
     void unfavouriteNotes(Integer favouriteNoteId) throws Exception;
     List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception;

}
