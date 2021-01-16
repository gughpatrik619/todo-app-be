package com.example.todoapp.model

enum class ERole(r: String) {
    ROLE_USER("USER"),
    ROLE_MODERATOR("MODERATOR"),
    ROLE_ADMIN("ADMIN");

    val shortName = r
}