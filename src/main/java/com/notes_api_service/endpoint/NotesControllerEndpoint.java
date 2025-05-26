package com.notes_api_service.endpoint;

import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesRequest;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.utils.CommonUtil;
import com.notes_api_service.utils.Constants;
import static com.notes_api_service.utils.Constants.ROLE_USER;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN_USER;
import static com.notes_api_service.utils.Constants.DEFAULT_PAGE_NO;
import static com.notes_api_service.utils.Constants.DEFAULT_PAGE_SIZE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequestMapping("/api/v1/notes")
@Tag(name = "Notes APIs", description = "All the notes operations APIs")
public interface NotesControllerEndpoint {

    @PostMapping(value = "/save",consumes = "multipart/form-data")
    @PreAuthorize(ROLE_ADMIN_USER)
    @Operation(summary = "save notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> saveNotes(@RequestParam
                                           @Parameter(description = "Json String Notes",required = true,
                                                   content = @Content(schema = @Schema(implementation = NotesRequest.class)))
                                           String notes , @RequestParam(required = false) MultipartFile file) throws Exception ;

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    @Operation(summary = "download notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception ;

    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    @Operation(summary = "get all notes endpoint",tags = {"Notes APIs"})
    public ResponseEntity<?> getAllNotes() ;

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "get all notes by user endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam (name = "pageNo" ,defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) ;

    @GetMapping("/search-notes")
    @PreAuthorize(ROLE_ADMIN_USER)
    @Operation(summary = "search notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> getAllNotesBySearch(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam (name = "pageNo" ,defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) ;

    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "delete notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception ;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "restore notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception ;

    @GetMapping("/recycle-bin/")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "get all notes from the recycle bin.",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception ;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "delete notes(hard) endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception ;

    @DeleteMapping("/delete-recycle/")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "remove all deleted notes from the recycle bin.",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> emptyRecycleBin() throws Exception ;

    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "get all favourite notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId ) throws Exception ;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "delete unfavourited notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favNoteId) throws Exception ;

    @GetMapping("/fav-notes")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "get all users favourite notes endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> getUserFavouriteNotes() throws Exception ;

    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    @Operation(summary = "copy notes by user id endpoint",tags = {"Notes APIs","User APIs"})
    public ResponseEntity<?> copyNotes(@PathVariable Integer id ) throws Exception ;
}
