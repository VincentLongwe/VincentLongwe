package com.example.datastore.service;

import com.example.datastore.entity.Role;
import com.example.datastore.dto.CreateAdminRequest;
import com.example.datastore.entity.User;
import com.example.datastore.exception.BadRequestException;
import com.example.datastore.exception.ForbiddenException;
import com.example.datastore.exception.ResourceNotFoundException;
import com.example.datastore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // ✅ Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Get one user
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->  new ResourceNotFoundException("User not found"));
    }

    // ✅ Change role
    public String updateUserRole(Long id, Role role) {
        User user = getUserById(id);
        user.setRole(role);
        userRepository.save(user);
        return "User role updated to " + role;
    }

    // ✅ Delete user
    public String deleteUser(Long id) {

        // 🔐 get currently logged-in username
        String currentUsername = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // ❌ prevent self-deletion
        if (userToDelete.getUsername().equals(currentUsername)) {
            throw new ForbiddenException("You cannot delete your own account");
        }

        userRepository.deleteById(id);

        return "User deleted successfully";
    }
    // ✅ Create admin
    public String createAdmin(CreateAdminRequest request) {

        // check if username exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists");
        }

        User admin = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_ADMIN)
                .build();

        userRepository.save(admin);

        return "Admin created successfully";
    }
}
