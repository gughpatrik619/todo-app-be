package com.example.todoapp.model.dto

import com.example.todoapp.model.EPriority
import com.example.todoapp.model.EState
import java.time.Instant

data class UpdateTodoDto(
    val title: String?,
    val description: String?,
    val dueDate: Instant?,
    val priority: EPriority?,
    val state: EState?
)