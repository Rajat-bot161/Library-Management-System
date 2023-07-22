package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dtos.InitiateTransactionRequest;
import com.example.LibraryManagementSystem.model.*;
import com.example.LibraryManagementSystem.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    StudentService studentService;

    @Autowired
    AdminService adminService;

    @Autowired
    BookService bookService;

    @Value("${student.allowed.max-books}")
    Integer maxBooksAllowed;

    @Value("${student.allowed.duration}")
    Integer duration;

    public String initiateTxn(InitiateTransactionRequest initiateTransactionRequest, Integer adminId) throws Exception {

        return initiateTransactionRequest.getTransactionType() == TransactionType.ISSUE ? issuance(initiateTransactionRequest,adminId)
            : returnBook(initiateTransactionRequest,adminId);
    }

    private String issuance(InitiateTransactionRequest initiateTransactionRequest, Integer adminId) throws Exception {
        Student student = studentService.findStudent(initiateTransactionRequest.getStudentId());
        Admin admin = adminService.find(adminId);
        List<Book> booklist = bookService.find("id", String.valueOf(initiateTransactionRequest.getBookId()));

        Book book = booklist !=null && booklist.size()>0 ? booklist.get(0) : null;

        if(student == null
                || admin == null
                || book == null
                || book.getStudent() !=null || student.getBookList().size() >= maxBooksAllowed) {
            throw new Exception("Invalid Request");
        }

        Transaction transaction = null;
        try {
            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .admin(admin)
                    .book(book)
                    .transactionType(initiateTransactionRequest.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING).build();

            transactionRepository.save(transaction);

            book.setStudent(student);
            bookService.CreateOrUpdate(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        } catch (Exception ex) {
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        }finally {
            transactionRepository.save(transaction);
        }
        return transaction.getTxnId();
    }

    private String returnBook(InitiateTransactionRequest initiateTransactionRequest, Integer adminId) throws Exception {

        Student student = studentService.findStudent(initiateTransactionRequest.getStudentId());
        Admin admin = adminService.find(adminId);
        List<Book> booklist = bookService.find("id", String.valueOf(initiateTransactionRequest.getBookId()));

        Book book = booklist !=null && booklist.size()>0 ? booklist.get(0) : null;

        if(student == null
                || admin == null
                || book == null
                || book.getStudent() == null
                || book.getStudent().getId() != student.getId()) {
            throw new Exception("Invalid Request");
        }

        //Getting the corresponding issuance transaction
        Transaction issuanceTxn = transactionRepository.findTopByStudentAndBookAndTransactionTypeOrderByIdDesc(student,
                book, TransactionType.ISSUE);

        if(issuanceTxn == null) {
            throw new Exception("Invalid Request");
        }

        Transaction transaction = null;
        try {
            Integer fine = calculateFine(issuanceTxn.getCreatedOn());

            transaction = Transaction.builder()
                    .txnId(UUID.randomUUID().toString())
                    .student(student)
                    .admin(admin)
                    .book(book)
                    .transactionType(initiateTransactionRequest.getTransactionType())
                    .transactionStatus(TransactionStatus.PENDING)
                    .fine(fine).build();

            transactionRepository.save(transaction);

            if (fine == 0) {
                book.setStudent(null);
                bookService.CreateOrUpdate(book);
                transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            }
        } catch(Exception ex) {
            transaction.setTransactionStatus(TransactionStatus.FAILURE);
        } finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxnId();
    }

    private Integer calculateFine(Date issuanceTime) {

        long issueTimeInMillis = issuanceTime.getTime();
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - issueTimeInMillis;

        long daysPassed = TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);

        if(daysPassed > duration) {
            return (int)(daysPassed - duration) * 1;
        }

        return 0;
    }

    public void payFine(Integer amount, Integer studentId, String txnId) throws Exception {

        Transaction returnTxn = transactionRepository.findByTxnId(txnId);

        Book book = returnTxn.getBook();

        if(returnTxn.getFine() == amount && book.getStudent() != null && book.getStudent().getId() == studentId) {
            returnTxn.setTransactionStatus(TransactionStatus.SUCCESS);
            book.setStudent(null);
            bookService.CreateOrUpdate(book);
            transactionRepository.save(returnTxn);
        } else {
            throw new Exception("invalid Request");
        }
    }
}
