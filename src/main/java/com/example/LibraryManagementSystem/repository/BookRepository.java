package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findByGenre(Genre genre);

    @Query("select b from Book b, Author a where b.my_author.id = a.id and a.name = ?1")
    List<Book> findByMy_Author_name(String authorName);

    List<Book> findByName(String bookName);

}
