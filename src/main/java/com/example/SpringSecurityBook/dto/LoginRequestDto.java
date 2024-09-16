package com.example.SpringSecurityBook.dto;

import lombok.Data;
// Передается при попытке аутентификации пользователя для получения JWT токена.
@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
