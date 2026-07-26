package com.example.datastore.dto;

import lombok.Data;

@Data
public class CreateAdminRequest {
    private String username;
    private String password;
}