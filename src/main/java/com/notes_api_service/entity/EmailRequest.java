package com.notes_api_service.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class EmailRequest {
    private String to;
    private String subject;
    private String title;
    private String message;
}
