package com.example.email_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

/**
 * This class configures the Thymeleaf template engine to send HTML emails.
 * @author Drew
 * @since 24/08/2023
 */
@Configuration
public class ThymeleafTemplateConfig {

    /**
     * Configures and provides an instance of SpringTemplateEngine.
     * @author Drew
     * @since 24/08/2023
     * @return An instance of SpringTemplateEngine configured with template resolver.
     */
    @Bean
    public SpringTemplateEngine springTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(htmlTemplateResolver());
        return templateEngine;
    }

    /**
     * Configures and provides a SpringResourceTemplateResolver for HTML templates.
     * @author Drew
     * @since 24/08/2023
     * @return An instance of SpringResourceTemplateResolver configured for HTML templates.
     */
    @Bean
    public SpringResourceTemplateResolver htmlTemplateResolver(){
        SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();

        // Specify the prefix where template files are located within the classpath.
        emailTemplateResolver.setPrefix("classpath:/templates/");

        // Specify the suffix for template files.
        emailTemplateResolver.setSuffix(".html");

        // Set the template mode to HTML (other options include XML, TEXT, etc.).
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);

        // Set the character encoding for template files.
        emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());

        return emailTemplateResolver;
    }
}

