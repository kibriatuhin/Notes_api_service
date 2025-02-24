package com.notes_api_service.dto;

import com.notes_api_service.entity.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String password;
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
}
