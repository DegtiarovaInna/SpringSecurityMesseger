package com.example.SpringSecurityBook.dto;

import lombok.Builder;
import lombok.Data;
// Возвращается клиенту после успешной аутентификации с токенами для доступа к защищенным ресурсам.
@Data
@Builder
public class LoginResponseDto {
    private String email;
    private String jwtToken;
    private String refreshToken;
}
