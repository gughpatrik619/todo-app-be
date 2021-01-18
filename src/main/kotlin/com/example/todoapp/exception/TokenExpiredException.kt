package com.example.todoapp.exception

data class TokenExpiredException(override val message: String) : RuntimeException()