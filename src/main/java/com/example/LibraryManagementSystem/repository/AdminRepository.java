package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
