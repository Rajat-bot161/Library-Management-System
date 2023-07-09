package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.CreateBookRequest;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;
    @PostMapping("/book")
    public void createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {

        bookService.CreateOrUpdate(createBookRequest.to());
    }

    //localhost://8080/book?key=author_name&value=Peter
    //localhost://8080/book?key=genre&value=FICTIONAL
    @GetMapping("/book")
    public List<Book> getBooks(@RequestParam("key") String key,
                               @RequestParam("value") String value) throws Exception {
        return bookService.find(key, value);
    }
}
