package com.example.todoapp.payload.response

import java.util.*

data class JwtResponse(
    val id: UUID?,
    val username: String?,
    val email: String?,
    val roles: List<String>,
    val token: String?,
    val type: String?
)