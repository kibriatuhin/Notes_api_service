package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.endpoint.NotesControllerEndpoint;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.entity.User;
import com.notes_api_service.service.NotesService;
import com.notes_api_service.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
public class NotesController implements NotesControllerEndpoint {
    @Autowired
    private NotesService notesService;

    @Override
    public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception {

         return notesService.saveNotes(notes,file) ?
                 CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED)
                 : CommonUtil.createErrorResponseMessage("Notes Not  Saved", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception {
        FileDetails fileDetails = notesService.getFileDetails(id);
        byte[] downloadFile = notesService.downloadFile(fileDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType
                .parseMediaType(CommonUtil.getContentType(fileDetails.getOrginalFileName())
                ));


        headers.setContentDispositionFormData("attachment", fileDetails.getOrginalFileName());

        return  ResponseEntity.ok().headers(headers).body(downloadFile);
    }

    @Override
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> NotesList = notesService.getAllNotes();
        return CollectionUtils.isEmpty(NotesList)
                ? ResponseEntity.noContent().build()
                : CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam (name = "pageNo" ,defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = "10") Integer pageSize) {

        Integer userId = CommonUtil.getLogedInUser().getId();
        NotesResponse NotesList = notesService.getAllNotesByUser(pageNo,pageSize);
        return CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getAllNotesBySearch(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam (name = "pageNo" ,defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = "10") Integer pageSize) {
        Integer userId = CommonUtil.getLogedInUser().getId();
        NotesResponse NotesList = notesService.getAllNotesBySearch(pageNo,pageSize,keyword);
        return CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {

        notesService.softDeleteNotesById(id);

        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {

        notesService.restoreNotesById(id);

        return CommonUtil.createBuildResponse("Notes restore Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {

        Integer userId = CommonUtil.getLogedInUser().getId();
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
        if (CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Notes Not available in recycle bin", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {

        notesService.hardDeleteNotesById(id);

        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> emptyRecycleBin() throws Exception {
        Integer userId = CommonUtil.getLogedInUser().getId();
        notesService.emptyRecycleByUser(userId);

        return CommonUtil.createBuildResponseMessage("Clear recycle-bin Success", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId ) throws Exception {

        notesService.favouriteNotes(noteId);

        return CommonUtil.createBuildResponseMessage("Favourite note added", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favNoteId) throws Exception {

        notesService.unfavouriteNotes(favNoteId);

        return CommonUtil.createBuildResponseMessage("Remove favourite note", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getUserFavouriteNotes() throws Exception {

       List<FavouriteNoteDto> favouriteNoteDto =  notesService.getUserFavouriteNotes();
        if (CollectionUtils.isEmpty(favouriteNoteDto)){
            return CommonUtil.createBuildResponseMessage("Favourite note not available", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(favouriteNoteDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> copyNotes(@PathVariable Integer id ) throws Exception {

      Boolean copyNotes =   notesService.copyNotes(id);
      if (copyNotes){
          return CommonUtil.createBuildResponseMessage("Copied Success", HttpStatus.OK);
      }

      return CommonUtil.createBuildResponseMessage("Copy failed ! try again.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
