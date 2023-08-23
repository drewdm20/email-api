package com.example.email_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the main class of the application
 * @author Drew
 * @since 23/08/2023
 */
@SpringBootApplication
@Slf4j
public class EmailApiApplication {

    public static void main(String[] args) {
        log.info("Starting application");
        SpringApplication.run(EmailApiApplication.class, args);
    }

}
