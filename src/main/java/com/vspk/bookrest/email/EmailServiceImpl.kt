package com.vspk.bookrest.email

import javax.mail.MessagingException
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

@Slf4j
class EmailServiceImpl(private val mailSender: JavaMailSender) : EmailService {

    companion object {
        private val log = LoggerFactory.getLogger(EmailServiceImpl::class.java)
    }

    @Value("\${spring.mail.username}")
    lateinit var username: String


    override fun send(emailTo: String, subject: String, message: String) {
        try {
            val mimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(mimeMessage, "utf-8")
            helper.setText(message, true)
            helper.setTo(emailTo)
            helper.setSubject(subject)
            helper.setFrom(username)
            mailSender.send(mimeMessage)
            log.info("email has been successfully sent to $emailTo")
        } catch (e: MessagingException) {
            log.error("Error, failed to send email")
        }
    }
}