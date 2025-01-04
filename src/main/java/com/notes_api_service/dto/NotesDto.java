package com.notes_api_service.dto;

import com.notes_api_service.entity.Category;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;
    private Integer createdBy;
    private Date createdOn;
    private Integer updatedBy;
    private Date updatedOn;

    private FileDetailsDto fileDetails;


    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FileDetailsDto{
        private Integer id;
        private String orginalFileName;
        private String displayFileName;

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto{
        private Integer id;
        private String name;
    }
}
