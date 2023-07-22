package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dtos.CreateStudentRequest;
import com.example.LibraryManagementSystem.model.SecuredUser;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.repository.StudentCacheRepository;
import com.example.LibraryManagementSystem.repository.StudentRepository;
import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    UserService userService;
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentCacheRepository studentCacheRepository;
    public void createStudent(Student student) {

        SecuredUser user = student.getSecuredUser();
        user = userService.save(user, Constants.STUDENT_USER);
        student.setSecuredUser(user);
        studentRepository.save(student);
    }

    public Student findStudent(int studentId) {
        Student student = studentCacheRepository.get(studentId);
        if(student != null) {
            return student;

        }
        student = studentRepository.findById(studentId).orElse(null);
        if(student != null) {
            studentCacheRepository.set(student);
        }
        return student;
    }


}
