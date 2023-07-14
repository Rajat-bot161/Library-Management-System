package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Admin;
import com.example.LibraryManagementSystem.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;
      public void create(Admin admin) {

          adminRepository.save(admin);
      }

    public Admin find(Integer adminId) {
          return adminRepository.findById(adminId).orElse(null);
    }

    public List<Admin> getAll() {
          return adminRepository.findAll();
    }
}
