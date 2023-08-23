package com.example.email_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sendinblue.ApiClient;
import sendinblue.auth.ApiKeyAuth;
import sibApi.TransactionalEmailsApi;

import javax.annotation.PostConstruct;

/**
 * This class configures the sendinblue api client
 * @author Drew
 * @since 23/08/2023
 */
@Configuration
public class SendInBlueApiConfig {

    // The api key is stored in the application.yml file
    @Value("${sendinblue.api.key}")
    private String apiKey;

    /**
     * This bean creates a reusable TransactionalEmailsApi object
     * @return TransactionalEmailsApi
     * @author Drew
     * @since 23/08/2023
     */
    @Bean
    public TransactionalEmailsApi transactionalEmailsApi(){
        return new TransactionalEmailsApi();
    }

    /**
     * This post construct autoconfigures the api client with the API key when the application starts
     * @author Drew
     * @since 23/08/2023
     */
    @PostConstruct
    public void configureApi(){
        ApiClient defaultClient = sendinblue.Configuration.getDefaultApiClient();
        ApiKeyAuth apiKeyAuth = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
        apiKeyAuth.setApiKey(apiKey);
    }
}
