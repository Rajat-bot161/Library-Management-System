package com.example.LibraryManagementSystem.dtos;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Genre;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequest {

    @NotBlank
    private String name;

    @NotNull
    private Genre genre;

    @NotBlank
    private String authorName;

    @NotBlank
    private String authorEmail;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .my_author(Author.builder()
                        .email(this.authorEmail)
                        .name(this.authorName)
                        .build())
                .build();
    }
}
