package com.example.SpringSecurityBook.controller;
import com.example.SpringSecurityBook.dto.MessageResponseDto;
import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.model.Message;
import com.example.SpringSecurityBook.service.AppUserService;
import com.example.SpringSecurityBook.service.JwtSecurityService;
import com.example.SpringSecurityBook.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    // /изменение
    @Autowired
    private AppUserService appUserService; // Сервис для работы с пользователями
    @Autowired
    private JwtSecurityService jwtService; // /изменение: Добавляем JwtSecurityService

    @PostMapping
    public ResponseEntity<MessageResponseDto> sendMessage(
            @AuthenticationPrincipal AppUser authenticatedUser,
            @RequestParam String toEmail,
            @RequestParam String text) {
        Message message = messageService.sendMessage(authenticatedUser.getEmail(), toEmail, text);

        MessageResponseDto responseDto = new MessageResponseDto();
        responseDto.setId(message.getId());
        responseDto.setSenderFullName(message.getSender().getFullName());
        responseDto.setRecipientFullName(message.getRecipient().getFullName());
        responseDto.setText(message.getText());

        return ResponseEntity.ok(responseDto);
    }
//    public ResponseEntity<Message> sendMessage(@RequestParam String fromEmail,
//                                               @RequestParam String toEmail,
//                                               @RequestParam String text) {
//        Message message = messageService.sendMessage(fromEmail, toEmail, text);
//        return ResponseEntity.ok(message);
//    }


    @GetMapping
    public ResponseEntity<List<MessageResponseDto>> getMessages(@AuthenticationPrincipal AppUser authenticatedUser) {
        List<MessageResponseDto> messages = messageService.getMessages(authenticatedUser.getEmail());
        return ResponseEntity.ok(messages);
    }

//    public ResponseEntity<List<Message>> getMessages(@RequestHeader("Authorization") String authHeader) {
//        // /изменение
//        String email = extractEmailFromToken(authHeader); // Извлекаем email из токена
//        // /изменение
//        AppUser user = appUserService.getDetailsService().loadUserByUsername(email);
//
//        List<Message> messages = messageService.getMessages(user);
//        return ResponseEntity.ok(messages);
//    }
//    public ResponseEntity<List<Message>> getMessages(@RequestParam String email) {
//        List<Message> messages = messageService.getMessages(email);
//        return ResponseEntity.ok(messages);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseDto> getMessageById(@PathVariable Long id) {
        MessageResponseDto messageDto = messageService.getMessageById(id);
        return ResponseEntity.ok(messageDto);
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<MessageResponseDto> replyToMessage(
            @AuthenticationPrincipal AppUser authenticatedUser,
            @PathVariable Long id,  // Изменено здесь: id вместо messageId
            @RequestParam String text) {
        Message replyMessage = messageService.replyToMessage(id, authenticatedUser.getEmail(), text);

        if (replyMessage != null) { // Добавлено: проверка на наличие ответа
            MessageResponseDto responseDto = new MessageResponseDto();
            responseDto.setId(replyMessage.getId());
            responseDto.setSenderFullName(replyMessage.getSender().getFullName());
            responseDto.setRecipientFullName(replyMessage.getRecipient().getFullName());
            responseDto.setText(replyMessage.getText());
            return ResponseEntity.ok(responseDto);
        } else {
            return ResponseEntity.notFound().build(); // Добавлено: возврат 404, если сообщение не найдено
        }
    }
//    public ResponseEntity<Message> replyToMessage(@PathVariable Long id,
//                                                  @RequestParam String fromEmail,
//                                                  @RequestParam String text) {
//        Message message = messageService.replyToMessage(id, fromEmail, text);
//        return message != null ? ResponseEntity.ok(message) : ResponseEntity.notFound().build();
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(
            @PathVariable Long id) { // Изменено здесь: убран authenticatedUser
        messageService.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
    //public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    //    messageService.deleteMessage(id);
    //    return ResponseEntity.noContent().build();
    //}

}
