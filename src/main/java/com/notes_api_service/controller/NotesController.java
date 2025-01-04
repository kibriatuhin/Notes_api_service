package com.notes_api_service.controller;

import com.notes_api_service.dto.CategoryResponseDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.service.NotesService;
import com.notes_api_service.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> saveNotes(@RequestParam String notes ,@RequestParam(required = false) MultipartFile file) throws Exception {


         return notesService.saveNotes(notes,file) ?
                 CommonUtil.createBuildResponseMessage("Notes saved success", HttpStatus.CREATED)
                 : CommonUtil.createErrorResponseMessage("Notes Not  Saved", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllNotes() {
        List<NotesDto> NotesList = notesService.getAllNotes();
        return CollectionUtils.isEmpty(NotesList)
                ? ResponseEntity.noContent().build()
                : CommonUtil.createBuildResponse(NotesList, HttpStatus.OK);

    }


}
