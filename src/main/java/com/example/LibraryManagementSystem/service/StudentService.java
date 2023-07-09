package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dtos.CreateStudentRequest;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;
    public void createStudent(Student student) {
          studentRepository.save(student);
    }

    public Student findStudent(int studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }


}
