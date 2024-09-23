package com.example.SpringSecurityBook.repository;

import com.example.SpringSecurityBook.model.AppUser;
import com.example.SpringSecurityBook.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface MessageRepository extends JpaRepository<Message, Long> {
//    List<Message> findByToEmailAndIsDeletedFalse(String toEmail);
//    List<Message> findByFromEmailAndIsDeletedFalse(String fromEmail);
// /изменение
List<Message> findByRecipientAndIsDeletedFalse(AppUser recipient); // Запрос по объекту пользователя
    // /изменение
    List<Message> findBySenderAndIsDeletedFalse(AppUser sender); // Запрос по отправителю

    List<Message> findByIsDeletedFalse();
}
