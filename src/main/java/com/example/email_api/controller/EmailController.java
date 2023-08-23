package com.example.email_api.controller;

import com.example.email_api.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is the controller that controls the endpoint to send an email
 * @author Drew
 * @since 23/08/2023
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * This endpoint sends an email to the user
     * @param email the email of the existing user
     * @throws Exception if the email fails to send
     * @author Drew
     * @since 23/08/2023
     */
    @PostMapping("/sendEmail")
    public void sendEmail(@RequestParam("email") String email) throws Exception {
        emailService.sendEmail(email);
        log.info("Email sent to: {}", email);
    }
}
