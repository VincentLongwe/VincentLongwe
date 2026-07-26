package com.example.datastore.controller;

import com.example.datastore.entity.Role;
import com.example.datastore.dto.CreateAdminRequest;
import com.example.datastore.entity.User;
import com.example.datastore.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // 👥 GET all users
    @GetMapping("/users")
    public List<User> getUsers() {
        return adminService.getAllUsers();
    }

    // 🔍 GET user by ID
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    // 🔄 UPDATE role
    @PutMapping("/users/{id}/role")
    public String updateRole(@PathVariable Long id,
                             @RequestParam Role role) {
        return adminService.updateUserRole(id, role);
    }

    // ❌ DELETE user
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    // CREATE admin
    @PostMapping("/create-admin")
    public String createAdmin(@RequestBody CreateAdminRequest request) {
        return adminService.createAdmin(request);
    }

}