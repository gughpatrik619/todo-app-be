package com.example.todoapp.model.dto

import com.example.todoapp.model.EPriority
import com.example.todoapp.model.EState
import java.time.Instant

data class TodoDto(
    val id: Long?,
    val title: String?,
    val description: String?,
    val created: Instant?,
    val lastUpdated: Instant?,
    val dueDate: Instant?,
    val priority: EPriority?,
    val state: EState?,
)