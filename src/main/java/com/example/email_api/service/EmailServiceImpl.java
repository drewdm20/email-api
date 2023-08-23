package com.example.email_api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;
import sendinblue.ApiException;
import sibApi.TransactionalEmailsApi;
import sibModel.*;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the methods defined in the Email Service
 * @author Drew
 * @since 23/08/2023
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService{

    // Values for the sender of the email
    @Value("${sendinblue.sender.email}")
    private String senderEmail;

    @Value("${sendinblue.sender.name}")
    private String senderName;

    private final TransactionalEmailsApi transactionalEmailsApi;

    private final SpringTemplateEngine springTemplateEngine;

    @Autowired
    public EmailServiceImpl(TransactionalEmailsApi transactionalEmailsApi, SpringTemplateEngine springTemplateEngine) {
        this.transactionalEmailsApi = transactionalEmailsApi;
        this.springTemplateEngine = springTemplateEngine;
    }

    /**
     * This function sends an email to the user
     * @param email the email of the user to send the email to
     * @throws Exception if the email cannot be sent
     * @author Drew
     * @since 23/08/2023
     */
    public void sendEmail(String email) throws Exception {
        log.info("Sending email to: {}", email);
        SendSmtpEmail sendSmtpEmail = getSmtpEmail(email);
        log.info("Sending email: {}", sendSmtpEmail);
        CreateSmtpEmail result = transactionalEmailsApi.sendTransacEmail(sendSmtpEmail);
        if (result != null){
            log.info("Email sent successfully");
        } else {
            throw new ApiException("Email failed to send");
        }
    }

    /**
     * This function creates an SMTP email that will be sent to the user
     * @author Drew
     * @since 23/08/2023
     * @param email the email of the user
     * @return a SMTP email object
     */
    private SendSmtpEmail getSmtpEmail(String email) {
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        setSender(sendSmtpEmail);
        setReplyTo(sendSmtpEmail);
        setRecipientList(email, sendSmtpEmail);
        setSubject(sendSmtpEmail);
        setContent(sendSmtpEmail);
        return sendSmtpEmail;
    }

    /**
     * Sets the subject of the SendSmtpEmail instance.
     * @author Drew
     * @since 23/08/2023
     * @param sendSmtpEmail The SendSmtpEmail instance to set the subject for.
     */
    private static void setSubject(SendSmtpEmail sendSmtpEmail) {
        sendSmtpEmail.setSubject("Forgot Password OTP");
    }

    /**
     * Sets the HTML content of the SendSmtpEmail instance by processing a Thymeleaf template.
     * @author Drew
     * @since 24/08/2023
     * @param sendSmtpEmail The SendSmtpEmail instance to set the HTML content for.
     */
    private void setContent(SendSmtpEmail sendSmtpEmail) {
        // Setting the HTML body (OTP still needs to be parsed into context variable)
        Context context = new Context();
        String content = springTemplateEngine.process("forgot-password-template", context);
        sendSmtpEmail.setHtmlContent(content);
    }

    /**
     * Sets the recipients of the SendSmtpEmail instance.
     * @author Drew
     * @since 23/08/2023
     * @param email The email address of the recipient.
     * @param sendSmtpEmail The SendSmtpEmail instance to set the recipients for.
     */
    private static void setRecipientList(String email, SendSmtpEmail sendSmtpEmail) {
        List<SendSmtpEmailTo> toList = new ArrayList<>();
        toList.add(new SendSmtpEmailTo().email(email));
        sendSmtpEmail.setTo(toList);
    }

    /**
     * Sets the reply-to email address and name for the SendSmtpEmail instance.
     * @author Drew
     * @since 23/08/2023
     * @param sendSmtpEmail The SendSmtpEmail instance to set the reply-to for.
     */
    private void setReplyTo(SendSmtpEmail sendSmtpEmail) {
        SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
        replyTo.setEmail(senderEmail);
        replyTo.setName(senderName);
        sendSmtpEmail.setReplyTo(replyTo);
    }

    /**
     * Sets the sender's email address and name for the SendSmtpEmail instance.
     * @author Drew
     * @since 23/08/2023
     * @param sendSmtpEmail The SendSmtpEmail instance to set the sender for.
     */
    private void setSender(SendSmtpEmail sendSmtpEmail) {
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(senderEmail);
        sender.setName(senderName);
        sendSmtpEmail.setSender(sender);
    }
}
