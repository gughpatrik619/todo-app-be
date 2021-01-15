package com.example.todoapp.payload.request

import javax.validation.constraints.NotBlank

data class SignupRequest(
    @NotBlank
    var username: String?,

    @NotBlank
    var email: String?,

    @NotBlank
    var password: String?
)