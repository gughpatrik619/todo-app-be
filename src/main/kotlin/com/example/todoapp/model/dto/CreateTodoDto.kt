package com.example.todoapp.model.dto

import com.example.todoapp.model.EPriority
import com.example.todoapp.model.EState
import com.example.todoapp.model.TodoEntity
import java.time.Instant

data class CreateTodoDto(
    val title: String?,
    val description: String?,
    val dueDate: Instant?,
    val priority: EPriority?
) {
    fun toEntity() =
        TodoEntity(
            id = null,
            created = null,
            lastUpdated = null,
            title = title,
            description = description,
            dueDate = dueDate,
            priority = priority,
            state = EState.TO_DO,
            user = null
        )
}