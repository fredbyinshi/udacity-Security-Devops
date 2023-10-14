package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class passwordService {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public String getBCryptedPasswd(String plainPasswd){
        return passwordEncoder.encode(plainPasswd);
    }
    public boolean validatePasswd(String passwd){
       return passwd.length() >= 8 && passwd.matches(".*[!@#$%^&*].*");
    }
}
