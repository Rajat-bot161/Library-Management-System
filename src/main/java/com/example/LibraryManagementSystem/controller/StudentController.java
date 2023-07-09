package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.CreateStudentRequest;
import com.example.LibraryManagementSystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest studentRequest) {
        studentService.createStudent(studentRequest.to());
    }

    @GetMapping("/student")
    public void findStudent(@RequestParam("id") int studentId) {
        studentService.findStudent(studentId);
    }
}
