package com.notes_api_service.service.notes_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notes_api_service.dto.NotesDto;
import com.notes_api_service.dto.NotesResponse;
import com.notes_api_service.entity.FileDetails;
import com.notes_api_service.entity.Notes;
import com.notes_api_service.exception.customException.ResourceNotFoundException;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

    @Override
    public Boolean saveNotes(String notes, MultipartFile file) throws Exception {
        ObjectMapper ob = new ObjectMapper();
        NotesDto notesDto = ob.readValue(notes, NotesDto.class);

        //validation notes
        validation.notesValidation(notesDto);

        Notes notesMap =  modelMapper.map(notesDto, Notes.class);
        FileDetails fileDetails = saveFileDetails(file);
        if (!ObjectUtils.isEmpty(fileDetails)) {
            notesMap.setFileDetails(fileDetails);

        }else {
            notesMap.setFileDetails(null);
        }


        Notes saveNotes =  notesRepository.save(notesMap);
        return !ObjectUtils.isEmpty(saveNotes);
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
       Page<Notes> pageNotes =  notesRepository.findByCreatedBy(id,pageable);
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
}
