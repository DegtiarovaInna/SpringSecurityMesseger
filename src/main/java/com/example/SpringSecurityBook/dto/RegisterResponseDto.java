package com.example.SpringSecurityBook.dto;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private Integer id;
    private String fullName;
    private String email;
    private String role;
}
