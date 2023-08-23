package com.example.email_api.service;

import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sendinblue.ApiException;
import sibApi.TransactionalEmailsApi;
import sibModel.*;

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

    // Secret values for the sender of the email
    @Value("${sendinblue.sender}")
    private String senderEmail;

    @Value("${sendinblue.sender.name}")
    private String senderName;

    private final TransactionalEmailsApi transactionalEmailsApi;

    @Autowired
    public EmailServiceImpl(TransactionalEmailsApi transactionalEmailsApi) {
        this.transactionalEmailsApi = transactionalEmailsApi;
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
     * This function sets the attributes of the SMTP email to be sent to the user
     * @param email the email of the user
     * @return a SMTP email object
     */
    @NotNull
    private SendSmtpEmail getSmtpEmail(String email) {
        // Creating object
        SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();// SendSmtpEmail | Values to send a transactional email
        // Setting email sender
        SendSmtpEmailSender sender = new SendSmtpEmailSender();
        sender.setEmail(senderEmail);
        sender.setName(senderName);
        sendSmtpEmail.setSender(sender);
        // Setting reply to email
        SendSmtpEmailReplyTo replyTo = new SendSmtpEmailReplyTo();
        replyTo.setEmail(senderEmail);
        replyTo.setName(senderName);
        sendSmtpEmail.setReplyTo(replyTo);
        // Setting the recipients of the email
        List<SendSmtpEmailTo> toList = new ArrayList<>();
        toList.add(new SendSmtpEmailTo().email(email));
        sendSmtpEmail.setTo(toList);
        // Setting the subject of the email
        sendSmtpEmail.setSubject("Forgot Password OTP");
        // Setting the HTML body (OTP still needs to be parsed and styling applied)
        sendSmtpEmail.setHtmlContent("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                " <title>Forgot Password OTP</title>\n" +
                "</head>\n" +
                "<body>\n" +
                " <h1>Forgot Password OTP</h1>\n" +
                " <p>Hi Farmer,</p>\n" +
                " <p>You have requested to reset your password. Your OTP is <strong>{{otp}}</strong>. This OTP is valid for 10 minutes.</p>\n" +
                " <p>If you did not request to reset your password, please ignore this email.</p>\n" +
                " <p>Thank you,</p>\n" +
                " <p>AgriFund</p>\n" +
                "</body>\n" +
                "</html>");
        return sendSmtpEmail;
    }
}
