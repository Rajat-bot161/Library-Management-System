package com.example.LibraryManagementSystem.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncodeConfig {

    @Bean
    PasswordEncoder getPE() {
        return new BCryptPasswordEncoder();
    }
}
