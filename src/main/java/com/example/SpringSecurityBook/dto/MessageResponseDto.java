package com.example.SpringSecurityBook.dto;

import lombok.Data;

@Data
public class MessageResponseDto {
    private Long id;
    private String senderFullName;
    private String recipientFullName;
    private String text;
}
