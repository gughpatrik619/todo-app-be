package com.example.todoapp.payload.request

import javax.validation.constraints.NotBlank

data class RefreshTokenRequest(
    @field:NotBlank(message = "Refresh token required")
    var refreshToken: String,
    
    var username: String
)