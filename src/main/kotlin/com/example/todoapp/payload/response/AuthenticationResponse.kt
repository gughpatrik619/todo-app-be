package com.example.todoapp.payload.response

import java.time.Instant
import java.util.*

data class AuthenticationResponse(
    val id: UUID?,
    val username: String?,
    val email: String?,
    val roles: List<String>,
    val jwtToken: String?,
    val type: String?,
    val expiresAt: Instant,
    val refreshToken: String?
)