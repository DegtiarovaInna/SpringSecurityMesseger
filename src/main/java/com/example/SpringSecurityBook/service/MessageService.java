package com.example.SpringSecurityBook.service;

import com.example.SpringSecurityBook.model.Message;
import com.example.SpringSecurityBook.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(String fromEmail, String toEmail, String text) {
        Message message = new Message();
        message.setFromEmail(fromEmail);
        message.setToEmail(toEmail);
        message.setText(text);
        message.setSentDate(LocalDateTime.now());
        return messageRepository.save(message);
    }

//    public List<Message> getMessages(String email) {
//        return messageRepository.findByToEmailAndIsDeletedFalse(email);
//    }
public List<Message> getMessages(String email) {
    if (email == null || email.isEmpty()) {
        return messageRepository.findByIsDeletedFalse();
    }
    return messageRepository.findByToEmailAndIsDeletedFalse(email);
} //добавляем проверку, чтобы вернуть все сообщения, если email не указан. Также добавим метод для фильтрации по isDeleted в репозитории.

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findById(id);
    }

    public Message replyToMessage(Long messageId, String fromEmail, String text) {
        Optional<Message> originalMessageOpt = messageRepository.findById(messageId);
        if (originalMessageOpt.isPresent()) {
            Message originalMessage = originalMessageOpt.get();
            Message replyMessage = new Message();
            replyMessage.setFromEmail(fromEmail);
            replyMessage.setToEmail(originalMessage.getFromEmail());
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
