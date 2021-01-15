package com.example.todoapp.service

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class MailContentBuilder(private val templateEngine: TemplateEngine) {

    fun buildActivationEmailContent(activationUrl: String): String {
        val context = Context()
        context.setVariable("activationUrl", activationUrl)
        context.setVariable("logo", "logo")
        return templateEngine.process("activation_email_template", context)
    }
}