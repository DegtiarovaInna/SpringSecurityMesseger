package com.example.SpringSecurityBook.controller;
import com.example.SpringSecurityBook.model.Message;
import com.example.SpringSecurityBook.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestParam String fromEmail,
                                               @RequestParam String toEmail,
                                               @RequestParam String text) {
        Message message = messageService.sendMessage(fromEmail, toEmail, text);
        return ResponseEntity.ok(message);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages(@RequestParam String email) {
        List<Message> messages = messageService.getMessages(email);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageService.getMessageById(id);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<Message> replyToMessage(@PathVariable Long id,
                                                  @RequestParam String fromEmail,
                                                  @RequestParam String text) {
        Message message = messageService.replyToMessage(id, fromEmail, text);
        return message != null ? ResponseEntity.ok(message) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return ResponseEntity.ok("Message marked as deleted");
    }
    //public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
    //    messageService.deleteMessage(id);
    //    return ResponseEntity.noContent().build();
    //}
}
