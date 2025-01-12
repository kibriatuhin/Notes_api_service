package com.notes_api_service.dto;

import com.notes_api_service.entity.Notes;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class FavouriteNoteDto {
    private Integer id;

    private NotesDto notes;

    private Integer userId;
}
