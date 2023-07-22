package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser,Integer> {

    SecuredUser findByusername(String name);
}
