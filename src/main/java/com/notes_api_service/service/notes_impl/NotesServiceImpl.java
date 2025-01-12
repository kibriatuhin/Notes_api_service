package com.notes_api_service.service.notes_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes_api_service.dto.FavouriteNoteDto;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FavouriteNote;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.entity.Notes;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
import com.notes_api_service.repository.FavouriteNotesRepository;
import com.notes_api_service.repository.FileRepository;
import com.notes_api_service.repository.NotesRepository;
import com.notes_api_service.service.NotesService;
import com.notes_api_service.utils.Validation;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotesServiceImpl implements NotesService {
    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    Validation validation;

    @Value("${file.upload.path}")
    String uploadPath ;

    @Autowired
    private FileRepository fileRepository ;

    @Autowired
    FavouriteNotesRepository favouriteNotesRepository;

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        notesDto.setIsDeleted(false);
        notesDto.setDeletedOn(null);

        //validation notes
        validation.notesValidation(notesDto);

        //update user if id is given
        if (!ObjectUtils.isEmpty(notesDto.getId())) {
            updateNotes(notesDto,file);
        }

        Notes notesMap =  modelMapper.map(notesDto, Notes.class);
        FileDetails fileDetails = saveFileDetails(file);
        if (!ObjectUtils.isEmpty(fileDetails)) {
            notesMap.setFileDetails(fileDetails);

        }else {
            if (!ObjectUtils.isEmpty(notesDto.getId())) {
                notesMap.setFileDetails(null);
            }

        }


        Notes saveNotes =  notesRepository.save(notesMap);
        return !ObjectUtils.isEmpty(saveNotes);
    }
    //update notes
    private void updateNotes(NotesDto notesDto, MultipartFile file) throws ResourceNotFoundException {
        Notes exitNotes = notesRepository.findById(notesDto.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Invalid notes id"));


        if (ObjectUtils.isEmpty(file)) {

            notesDto.setFileDetails(modelMapper.map(Optional.ofNullable(exitNotes.getFileDetails()), NotesDto.FileDetailsDto.class));
            //notesDto.setTitle();
        }
    }

    @Override
    public byte[] downloadFile(FileDetails fileDetails) throws Exception {


        InputStream io = new FileInputStream(fileDetails.getPath());

        return StreamUtils.copyToByteArray(io);

    }

    @Override
    public FileDetails getFileDetails(Integer id) throws Exception {
        return fileRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("File is not available"));
    }

    @Override
    public NotesResponse getAllNotesByUser(Integer id,Integer pageNo , Integer pageSize) {

       Pageable pageable =  PageRequest.of(pageNo,pageSize);
       Page<Notes> pageNotes =  notesRepository.findByCreatedByAndIsDeletedFalse(id,pageable);
       List<NotesDto> notesDto =   pageNotes.get().map(n -> modelMapper.map(n,NotesDto.class)).toList();
        return NotesResponse.builder()
                .notes(notesDto)
                .pageNo(pageNotes.getNumber())
                .pageSize(pageNotes.getSize())
                .totalElements(pageNotes.getTotalElements())
                .totalPages(pageNotes.getTotalPages())
                .isFirst(pageNotes.isFirst())
                .isLast(pageNotes.isLast())
                .build();
    }

    @Override
    public void softDeleteNotesById(Integer id) throws ResourceNotFoundException {
       Notes notes = notesRepository.findById(id)
               .orElseThrow(()-> new ResourceNotFoundException("Notes id invalid ! Not found "));
       notes.setIsDeleted(true);
       notes.setDeletedOn(LocalDateTime.now());
       notesRepository.save(notes);

    }

    @Override
    public void hardDeleteNotesById(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Notes id invalid ! Not found "));
        if (notes.getIsDeleted()) {
            notesRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException("Sorry you can't hard delete directly");
        }
    }

    @Override
    public void restoreNotesById(Integer id) throws Exception {
        Notes notes = notesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Notes id invalid ! Not found "));
        notes.setIsDeleted(false);
        notes.setDeletedOn(null);
        notesRepository.save(notes);
    }

    //save file
    private FileDetails saveFileDetails(MultipartFile file) throws IOException {
        if (!ObjectUtils.isEmpty(file) && !file.isEmpty()) {
            String orginalFileName = file.getOriginalFilename();
            String extention = FilenameUtils.getExtension(orginalFileName);
            //format check
            List<String> allowsExtention =  Arrays.asList("pdf","xlsx","jpg","png");
            if (!allowsExtention.contains(extention)) {
                throw new IllegalArgumentException("invalid file format ! upload only (pdf,xlsx,jpg,png)");
            }

            //random file name generate
            String randomNumber = UUID.randomUUID().toString();
            String uploadFileName = randomNumber+ "." + extention;
            //folder check
            File saveFile = new File(uploadPath);
            if (!saveFile.exists()) {
                saveFile.mkdirs();
            }
            //com.notes_api_service/notesfile/java.pdf
            String storePath = uploadPath.concat(uploadFileName);
            //file upload
            long upload =  Files.copy(file.getInputStream(), Paths.get(storePath));
            //after upload local then save database
            if (upload!=0){
                //set filedetails
                FileDetails fileDetails = new FileDetails();
                fileDetails.setOrginalFileName(orginalFileName);
                fileDetails.setUploadFileName(uploadFileName);
                fileDetails.setFileSize(file.getSize());
                fileDetails.setDisplayFileName(getDisplayFileName(orginalFileName));
                fileDetails.setPath(storePath);


                return fileRepository.save(fileDetails);
            }
            return null;
        }
        return null;
    }
    //get display name for file
    private String getDisplayFileName(String orginalFileName) {
        //javaProgramming.pdf
        //pdf
        String extention = FilenameUtils.getExtension(orginalFileName);
        //javaProgramming
        String fileName = FilenameUtils.removeExtension(orginalFileName);

        if (fileName.length()>8){
            fileName = fileName.substring(0,7);
        }
        fileName = fileName+"."+extention;
        return fileName;

    }

    @Override
    public List<NotesDto> getAllNotes() {

         return notesRepository.findAll().stream()
                 .map(notes-> modelMapper
                         .map(notes,NotesDto.class)).toList();


    }

    @Override
    public List<NotesDto> getUserRecycleBinNotes(Integer userId) {
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        return recycleNotes.stream()
                .map(notes-> modelMapper.map(notes,NotesDto.class)).toList();
    }

    @Override
    public void emptyRecycleByUser(Integer userId) {
        List<Notes> recycleNotes = notesRepository.findByCreatedByAndIsDeletedTrue(userId);
        if (!CollectionUtils.isEmpty(recycleNotes)) {
            notesRepository.deleteAll(recycleNotes);
        }

    }

    @Override
    public void favouriteNotes(Integer noteId) throws Exception {
        int userId=1;
        Notes notes = notesRepository.findById(noteId)
                .orElseThrow(()-> new ResourceNotFoundException("Notes not found | Invalid notes Id"));
        FavouriteNote favouriteNote = FavouriteNote.builder()
                .notes(notes)
                .userId(userId)
                .build();
        favouriteNotesRepository.save(favouriteNote);

    }

    @Override
    public void unfavouriteNotes(Integer favouriteNoteId) throws Exception {
        FavouriteNote notes = favouriteNotesRepository.findById(favouriteNoteId)
                .orElseThrow(()-> new ResourceNotFoundException("favourite Note Id not found "));
        favouriteNotesRepository.delete(notes);
    }

    @Override
    public List<FavouriteNoteDto> getUserFavouriteNotes() throws Exception {
        int userId=1;


        return favouriteNotesRepository.findByUserId(userId).stream()
                .map(notes-> modelMapper.map(notes,FavouriteNoteDto.class)).toList();
    }
}
