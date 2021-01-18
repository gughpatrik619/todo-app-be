package com.example.todoapp.exception

data class InvalidTokenException(override val message: String) : RuntimeException()