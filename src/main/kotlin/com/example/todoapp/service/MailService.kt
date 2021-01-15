package com.example.todoapp.service

import com.example.todoapp.model.ActivationEmail
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class MailService(
    private val mailSender: JavaMailSender,
    private val mailContentBuilder: MailContentBuilder
) {
    @Async
    fun sendActivationEmail(activationEmail: ActivationEmail) {
        val messagePreparator = MimeMessagePreparator { mimeMessage ->
            val messageHelper = MimeMessageHelper(mimeMessage, true, "UTF-8")
            messageHelper.setTo(activationEmail.recipient!!)
            messageHelper.setSubject(activationEmail.subject!!)
            messageHelper.setText(mailContentBuilder.buildActivationEmailContent(activationEmail.activationUrl!!), true)
            messageHelper.addInline("logo", ClassPathResource("logo.png"))
        }
        sendEmail(messagePreparator)
    }

    private fun sendEmail(messagePreparator: MimeMessagePreparator) {
        try {
            mailSender.send(messagePreparator)
        } catch (e: MailException) {
            e.printStackTrace()
        }
    }
}