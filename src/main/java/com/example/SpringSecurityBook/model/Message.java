package com.example.SpringSecurityBook.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "from_email", nullable = false)
//    private String fromEmail;
//
//    @Column(name = "to_email", nullable = false)
//    private String toEmail;

// /изменение
@ManyToOne
@JoinColumn(name = "sender_id", nullable = false) // Связываем с таблицей пользователей
private AppUser sender;

    // /изменение
    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false) // Связываем с таблицей пользователей
    private AppUser recipient;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "sent_date", nullable = false)
    private LocalDateTime sentDate;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;
}
