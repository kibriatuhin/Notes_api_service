package com.notes_api_service.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private StatusDto status;
    private List<RoleDto> roles;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoleDto{
        private Integer id;
        private String name;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class StatusDto{
        private Integer id;
        private  Boolean isActive;
    }
}

