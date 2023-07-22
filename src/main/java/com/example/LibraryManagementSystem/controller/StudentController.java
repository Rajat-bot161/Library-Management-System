package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.CreateStudentRequest;
import com.example.LibraryManagementSystem.model.SecuredUser;
import com.example.LibraryManagementSystem.model.Student;
import com.example.LibraryManagementSystem.service.StudentService;
import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/student")
    public void createStudent(@RequestBody @Valid CreateStudentRequest studentRequest) {
        studentService.createStudent(studentRequest.to());
    }

    // Secured
    //Only for admin so that they can see any student's details
    @GetMapping("/student-by-id/{id}")
    public Student findStudentById(@PathVariable("id") int studentId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();

        for(GrantedAuthority grantedAuthority: user.getAuthorities()) {
            String[] authorities = grantedAuthority.getAuthority().split(Constants.DELIMITER);
            boolean isCalledByAdmin = Arrays.stream(authorities).anyMatch(x -> Constants.STUDENT_INFO_AUTHORITY.equals(x));
            if(isCalledByAdmin) {
                return studentService.findStudent(studentId);
            }

        }
       throw new Exception("User is not authorized to do this");
    }


    //Only for student to see their own details
    @GetMapping("/student")
    public Student findStudent() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user = (SecuredUser) authentication.getPrincipal();
        int studentId = user.getStudent().getId();

        return studentService.findStudent(studentId);
    }
}
