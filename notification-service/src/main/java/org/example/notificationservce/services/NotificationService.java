package org.example.notificationservce.services;

import lombok.RequiredArgsConstructor;
import org.example.notificationservce.DTO.NotificationRequestDTO;
import org.example.notificationservce.models.NotificationTemplate;
import org.example.notificationservce.repositories.NotificationTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationTemplateRepository templateRepository;
    private final EmailService emailService;
    private final SmsService smsService;

    public void sendNotification(NotificationRequestDTO request) {

        NotificationTemplate template =
                templateRepository.findByEventType(request.getEventType())
                        .orElseThrow(() ->
                                new RuntimeException("Template not found"));

        String emailMsg = replacePlaceholders(
                template.getEmailTemplate(),
                request.getPlaceholders());

        String smsMsg = replacePlaceholders(
                template.getSmsTemplate(),
                request.getPlaceholders());

        emailService.sendEmail(request.getEmail(), emailMsg);

        smsService.sendSms(request.getPhoneNumber(), smsMsg);
    }

    private String replacePlaceholders(
            String template,
            Map<String, String> placeholders) {

        String result = template;

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {

            result = result.replace(
                    "{{" + entry.getKey() + "}}",
                    entry.getValue());
        }

        return result;
    }
}