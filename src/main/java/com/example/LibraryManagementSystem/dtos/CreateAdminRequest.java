package com.example.LibraryManagementSystem.dtos;

import com.example.LibraryManagementSystem.model.Admin;
import com.example.LibraryManagementSystem.model.SecuredUser;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Admin to() {
        return Admin.builder()
                .name(this.name)
                .email(this.email)
                .securedUser(SecuredUser.builder()
                        .username(this.username)
                        .password(this.password)
                        .build())
                .build();
    }

}
