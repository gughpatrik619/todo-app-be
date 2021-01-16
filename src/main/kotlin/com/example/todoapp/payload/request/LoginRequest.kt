package com.example.todoapp.payload.request

import javax.validation.constraints.NotBlank

data class LoginRequest(
    @field:NotBlank(message = "Username required")
    var username: String,

    @field:NotBlank(message = "Password required")
    var password: String
)