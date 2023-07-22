package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Admin;
import com.example.LibraryManagementSystem.model.SecuredUser;
import com.example.LibraryManagementSystem.repository.AdminRepository;
import com.example.LibraryManagementSystem.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;
      public void create(Admin admin) {
          SecuredUser securedUser = admin.getSecuredUser();
          securedUser = userService.save(securedUser, Constants.ADMIN_USER);

          admin.setSecuredUser(securedUser);
          adminRepository.save(admin);
      }

    public Admin find(Integer adminId) {
          return adminRepository.findById(adminId).orElse(null);
    }

    public List<Admin> getAll() {
          return adminRepository.findAll();
    }
}
