package com.example.datastore.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public String getUserProfile() {
        return "User profile accessed";
    }
}