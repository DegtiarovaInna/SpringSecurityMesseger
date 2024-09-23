package com.example.SpringSecurityBook.service;

import com.example.SpringSecurityBook.dto.MessageResponseDto;
import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.model.Message;
import com.example.SpringSecurityBook.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AppUserService appUserService;

    public Message sendMessage(String fromEmail, String toEmail, String text) {
        Message message = new Message();
        message.setSender(appUserService.loadUserByEmail(fromEmail)); // /изменение: устанавливаем отправителя
        message.setRecipient(appUserService.loadUserByEmail(toEmail)); // /изменение: устанавливаем получателя
        message.setText(text);
        message.setSentDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

//    public List<Message> getMessages(String email) {
//        return messageRepository.findByToEmailAndIsDeletedFalse(email);
//    }
public List<MessageResponseDto> getMessages(String email) {
    AppUser user = appUserService.loadUserByEmail(email);
    List<Message> messages = messageRepository.findByRecipientAndIsDeletedFalse(user);

    return messages.stream().map(message -> {
        MessageResponseDto dto = new MessageResponseDto();
        dto.setId(message.getId());
        dto.setSenderFullName(message.getSender().getFullName());
        dto.setRecipientFullName(message.getRecipient().getFullName());
        dto.setText(message.getText());
        return dto;
    }).collect(Collectors.toList());
}
    public MessageResponseDto getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .filter(m -> !m.getIsDeleted()) // Проверка на статус удаления
                .orElseThrow(() -> new RuntimeException("Message not found or has been deleted"));

        MessageResponseDto dto = new MessageResponseDto();
        dto.setId(message.getId());
        dto.setSenderFullName(message.getSender().getFullName());
        dto.setRecipientFullName(message.getRecipient().getFullName());
        dto.setText(message.getText());
        return dto;
    }

    public Message replyToMessage(Long messageId, String fromEmail, String text) {
        Optional<Message> originalMessageOpt = messageRepository.findById(messageId);
        if (originalMessageOpt.isPresent()) {
            Message originalMessage = originalMessageOpt.get();
            Message replyMessage = new Message();
            replyMessage.setSender(appUserService.loadUserByEmail(fromEmail)); // /изменение: устанавливаем отправителя
            replyMessage.setRecipient(originalMessage.getSender()); // /изменение: получатель - оригинальный отправитель
            replyMessage.setText(text);
            replyMessage.setSentDate(LocalDateTime.now());
            return messageRepository.save(replyMessage);
        }
        return null;
    }

    public void deleteMessage(Long id) {
        Optional<Message> messageOpt = messageRepository.findById(id);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            message.setIsDeleted(true);
            messageRepository.save(message);
        }
    }
}
