package com.notes_api_service.dto;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {
    private Integer id;
    private String title;
    private StatusDto status;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
   public static class StatusDto {
        private Integer id;
        private String  name;
    }
}
