package com.example.todoapp.payload.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class SignupRequest(
    @field:NotBlank(message = "Username required")
    var username: String,

    @field:NotBlank(message = "Email required")
    @field:Email(message = "Email must have valid format", regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\$")
    var email: String,

    @field:NotBlank(message = "Password required")
    var password: String
)