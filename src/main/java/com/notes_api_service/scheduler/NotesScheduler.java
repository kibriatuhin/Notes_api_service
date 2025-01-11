package com.notes_api_service.scheduler;

import com.notes_api_service.entity.Notes;
import com.notes_api_service.repository.NotesRepository;
import com.notes_api_service.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NotesScheduler {
    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNoteSchedule(){
        LocalDateTime cuttOffDate = LocalDateTime.now().minusDays(7);
        List<Notes> deleteNotes = notesRepository.findAllByIsDeletedAndDeletedOnBefore(true,cuttOffDate);
        notesRepository.deleteAll(deleteNotes);
        //System.out.println(cuttOffDate);
    }
}
