package com.vspk.bookrest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private int port;

//    @Value("${spring.mail.protocol}")
//    private String protocol;

    @Bean
    public JavaMailSender getJavaMailSender() {
        var mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        //mailSender.setProtocol(protocol);
        mailSender.setUsername(username);
        mailSender.setPassword(password);


        var properties = mailSender.getJavaMailProperties();

        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.debug", "true");

        return mailSender;
    }
}
