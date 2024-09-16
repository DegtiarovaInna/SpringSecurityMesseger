package com.example.SpringSecurityBook.dto;

import lombok.Data;
//Используется для передачи данных при регистрации нового пользователя через API.
@Data
public class RegisterRequestDto {
    private String fullName;
    private String email;
    private String password;
}
