package com.notes_api_service.endpoint;

import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.utils.CommonUtil;
import com.notes_api_service.utils.Constants;
import static com.notes_api_service.utils.Constants.ROLE_USER;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN;
import static com.notes_api_service.utils.Constants.ROLE_ADMIN_USER;
import static com.notes_api_service.utils.Constants.DEFAULT_PAGE_NO;
import static com.notes_api_service.utils.Constants.DEFAULT_PAGE_SIZE;
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
public interface NotesControllerEndpoint {
    @PostMapping("/save")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> saveNotes(@RequestParam String notes , @RequestParam(required = false) MultipartFile file) throws Exception ;

    @GetMapping("/download/{id}")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception ;
    @GetMapping("/")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<?> getAllNotes() ;

    @GetMapping("/user-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getAllNotesByUser(
            @RequestParam (name = "pageNo" ,defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) ;

    @GetMapping("/search-notes")
    @PreAuthorize(ROLE_ADMIN_USER)
    public ResponseEntity<?> getAllNotesBySearch(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam (name = "pageNo" ,defaultValue = DEFAULT_PAGE_NO) Integer pageNo,
            @RequestParam(name = "pageSize" ,defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) ;

    @GetMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception ;

    @GetMapping("/restore/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception ;

    @GetMapping("/recycle-bin/")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserRecycleBinNotes() throws Exception ;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception ;

    @DeleteMapping("/delete-recycle/")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> emptyRecycleBin() throws Exception ;

    @GetMapping("/fav/{noteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> favouriteNotes(@PathVariable Integer noteId ) throws Exception ;

    @DeleteMapping("/un-fav/{favNoteId}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> unfavouriteNotes(@PathVariable Integer favNoteId) throws Exception ;

    @GetMapping("/fav-notes")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> getUserFavouriteNotes() throws Exception ;

    @GetMapping("/copy/{id}")
    @PreAuthorize(ROLE_USER)
    public ResponseEntity<?> copyNotes(@PathVariable Integer id ) throws Exception ;
}
