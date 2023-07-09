package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    public Author getOrCreate(Author author) {
        Author authorRetrieved = authorRepository.findByEmail(author.getEmail());
        if(authorRetrieved == null) {
            authorRetrieved = authorRepository.save(author);
        }
        return authorRetrieved;
    }
}
