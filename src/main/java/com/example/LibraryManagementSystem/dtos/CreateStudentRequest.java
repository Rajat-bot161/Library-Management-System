package com.example.LibraryManagementSystem.dtos;

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

    public Student to(){
        return Student.builder()
                .name(this.name)
                .email(this.email)
                .age(this.age)
                .build();
    }
}
