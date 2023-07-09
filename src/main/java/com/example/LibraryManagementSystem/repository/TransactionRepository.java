package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    //@Query("select * from transaction where student_id = ?1 and book_id = ?2 and transactionType =?3 order by id desc limit 1")
    Transaction findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);

    Transaction findByTxnId(String txnId);
}
