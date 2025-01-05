package com.notes_api_service.dto;

import com.notes_api_service.entity.Notes;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotesResponse {
    private List<NotesDto> notes;
    private Integer pageNo;
    private  Integer pageSize;
    private  Long totalElements;
    private Integer totalPages;
    private Boolean isFirst;
    private  Boolean isLast;
}
