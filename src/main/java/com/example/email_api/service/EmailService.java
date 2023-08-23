package com.example.email_api.service;

/**
 * This interface abstracts the methods implemented in the Email Service
 * @author Drew
 * @since 23/08/2023
 */
public interface EmailService {
    void sendEmail(String email) throws Exception;
}
