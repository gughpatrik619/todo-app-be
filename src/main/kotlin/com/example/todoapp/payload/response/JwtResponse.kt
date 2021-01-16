package com.example.todoapp.payload.response

import com.example.todoapp.model.Todo
import java.util.*

data class JwtResponse(
    val id: UUID?,
    val username: String?,
    val email: String?,
    val roles: List<String>,
    val todos: List<Todo>,
    val token: String?,
    val type: String?
)