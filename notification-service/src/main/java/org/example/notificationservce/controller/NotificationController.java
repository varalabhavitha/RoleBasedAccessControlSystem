package org.example.notificationservce.controller;

import lombok.RequiredArgsConstructor;
import org.example.notificationservce.DTO.NotificationRequestDTO;
import org.example.notificationservce.services.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestBody NotificationRequestDTO request) {

        notificationService.sendNotification(request);

        return ResponseEntity.ok("Notification sent successfully");
    }
}