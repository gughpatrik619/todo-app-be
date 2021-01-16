package com.example.todoapp.exception

import java.time.Instant

data class CustomErrorResponse(
    var timestamp: Instant,
    var message: String,
    var status: Int,
    var fieldErrors: Map<String, String?>
)