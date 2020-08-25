package com.project.hotel.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component


@Component
class MailService(val mailSender: JavaMailSender) {
    @Value("\${spring.mail.username}")
    lateinit var username: String
    fun sent(emailTo: String, subject: String, message: String) {
        val mailMessage = SimpleMailMessage()

        mailMessage.setFrom(username)
        mailMessage.setTo(emailTo)
        mailMessage.setSubject(subject)
        mailMessage.setText(message)

        mailSender.send(mailMessage)
    }
}