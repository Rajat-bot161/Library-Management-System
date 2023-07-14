package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dtos.CreateAdminRequest;
import com.example.LibraryManagementSystem.model.Admin;
import com.example.LibraryManagementSystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminService adminService;
    @PostMapping("/admin")
    public void createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {

        adminService.create(createAdminRequest.to());
    }

    @GetMapping("/all")
    public List<Admin> getAll() {
        return adminService.getAll();
    }
}
