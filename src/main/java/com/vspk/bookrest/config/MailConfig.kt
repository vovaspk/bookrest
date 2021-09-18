package com.vspk.bookrest.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
open class MailConfig {
    @Value("{spring.mail.host}")
    private lateinit var host: String

    @Value("{spring.mail.username}")
    private lateinit var username: String

    @Value("{spring.mail.password}")
    private lateinit var password: String

    @Value("\${spring.mail.port}")
    private lateinit var port: String

    @Bean
    open fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = host
        mailSender.port = port.toInt()
        mailSender.username = username
        mailSender.password = password
        val properties = mailSender.javaMailProperties
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.starttls.enable", "true")
        properties["mail.transport.protocol"] = "smtp"
        properties["mail.smtp.ssl.enable"] = "true"
        properties["mail.debug"] = "true"
        return mailSender
    }
}