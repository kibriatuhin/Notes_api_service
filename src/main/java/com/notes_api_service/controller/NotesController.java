package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FileDetails;
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
@RequestMapping("/api/v1/notes")
public class NotesController {
    @Autowired
    private NotesService notesService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception {

         return notesService.saveNotes(notes,file) ?
                 CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED)
                 : CommonUtil.createErrorResponseMessage("Notes Not  Saved", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/download/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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




    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> NotesList = notesService.getAllNotes();
        return CollectionUtils.isEmpty(NotesList)
                ? ResponseEntity.noContent().build()
                : CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }

    @GetMapping("/user-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam (name = "pageNo" ,defaultValue = "0") Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = "10") Integer pageSize) {

        Integer userId = 1;
        NotesResponse NotesList = notesService.getAllNotesByUser(userId,pageNo,pageSize);
        return CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception {

        notesService.softDeleteNotesById(id);

        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @GetMapping("/restore/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception {

        notesService.restoreNotesById(id);

        return CommonUtil.createBuildResponse("Notes restore Success", HttpStatus.OK);
    }

    @GetMapping("/recycle-bin/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception {

        Integer userId = 1;
        List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
        if (CollectionUtils.isEmpty(notes)){
            return CommonUtil.createBuildResponseMessage("Notes Not available in recycle bin", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(notes, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception {

        notesService.hardDeleteNotesById(id);

        return CommonUtil.createBuildResponseMessage("Delete Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete-recycle/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> emptyRecycleBin() throws Exception {
        Integer userId = 1;
        notesService.emptyRecycleByUser(userId);

        return CommonUtil.createBuildResponseMessage("Clear recycle-bin Success", HttpStatus.OK);
    }

    @GetMapping("/fav/{noteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId ) throws Exception {

        notesService.favouriteNotes(noteId);

        return CommonUtil.createBuildResponseMessage("Favourite note added", HttpStatus.OK);
    }
    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favNoteId) throws Exception {

        notesService.unfavouriteNotes(favNoteId);

        return CommonUtil.createBuildResponseMessage("Remove favourite note", HttpStatus.OK);
    }
    @GetMapping("/fav-notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserFavouriteNotes() throws Exception {

       List<FavouriteNoteDto> favouriteNoteDto =  notesService.getUserFavouriteNotes();
        if (CollectionUtils.isEmpty(favouriteNoteDto)){
            return CommonUtil.createBuildResponseMessage("Favourite note not available", HttpStatus.NOT_FOUND);
        }
        return CommonUtil.createBuildResponse(favouriteNoteDto, HttpStatus.OK);
    }

    @GetMapping("/copy/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> copyNotes(@PathVariable Integer id ) throws Exception {

      Boolean copyNotes =   notesService.copyNotes(id);
      if (copyNotes){
          return CommonUtil.createBuildResponseMessage("Copied Success", HttpStatus.OK);
      }

      return CommonUtil.createBuildResponseMessage("Copy failed ! try again.",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
