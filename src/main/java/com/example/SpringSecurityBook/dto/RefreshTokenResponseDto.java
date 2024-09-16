package com.example.SpringSecurityBook.dto;

import lombok.Data;
// DTO для ответа после успешного обновления токена.
@Data
public class RefreshTokenResponseDto {
    private String jwtToken;
    private String refreshToken;
}
