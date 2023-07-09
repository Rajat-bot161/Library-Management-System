package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Genre;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    AuthorService authorService;

    @Autowired
    BookRepository bookRepository;
    public void CreateOrUpdate(Book book){

        Author bookauthor = book.getMy_author();
        Author savedAuthor = authorService.getOrCreate(bookauthor);
        book.setMy_author(savedAuthor);
        bookRepository.save(book);
    }

    public List<Book> find(String key, String value) throws Exception {

        switch(key) {
            case "id":
            {
                Optional<Book> book = bookRepository.findById(Integer.parseInt(value));
                if (book.isPresent()) {
                    return Arrays.asList(book.get());
                } else {
                    return new ArrayList<>();
                }
            }
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(value));
            case "author_name":
                return bookRepository.findByMy_Author_name(value);
            case "name":
                return bookRepository.findByName(value);
            default:
                throw new Exception("Search key not valid " + key);
        }
    }
}
