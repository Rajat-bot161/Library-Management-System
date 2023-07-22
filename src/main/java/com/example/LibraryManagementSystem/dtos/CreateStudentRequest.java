package com.example.LibraryManagementSystem.dtos;

import com.example.LibraryManagementSystem.model.SecuredUser;
import com.example.LibraryManagementSystem.model.Student;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private Integer age;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public Student to(){
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .securedUser(
                        SecuredUser.builder()
                                .username(this.username)
                                .password(this.password)
                                .build()
                )
                .age(this.age)
                .build();
    }
}
