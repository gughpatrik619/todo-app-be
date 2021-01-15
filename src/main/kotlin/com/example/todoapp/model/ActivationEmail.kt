package com.example.todoapp.model

data class ActivationEmail(
    val recipient: String?,
    val subject: String?,
    val activationUrl: String?
)