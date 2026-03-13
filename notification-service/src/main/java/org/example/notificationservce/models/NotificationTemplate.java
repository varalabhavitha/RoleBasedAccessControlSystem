package org.example.notificationservce.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_template")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String emailTemplate;

    @Column(columnDefinition = "TEXT")
    private String smsTemplate;
}