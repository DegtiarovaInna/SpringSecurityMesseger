package com.example.SpringSecurityBook.dto;

import lombok.Data;
// Используется для отправки запроса на получение нового JWT токена, когда старый истек.
@Data
public class RefreshTokenRequestDto {
    private String refreshToken;
}
