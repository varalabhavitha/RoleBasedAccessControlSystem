package org.example.notificationservce.DTO;

import lombok.Data;

import java.util.Map;

@Data
public class NotificationRequestDTO {

    private String eventType;
    private String email;
    private String phoneNumber;

    private Map<String, String> placeholders;
}
