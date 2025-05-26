package com.notes_api_service.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotesRequest {

    private String title;

    private String description;

    private CategoryDto category;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto{
        private Integer id;
        private String name;
    }
}
