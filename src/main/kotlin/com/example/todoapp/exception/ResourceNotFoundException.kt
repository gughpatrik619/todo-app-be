package com.example.todoapp.exception

data class ResourceNotFoundException(override val message: String) : RuntimeException()