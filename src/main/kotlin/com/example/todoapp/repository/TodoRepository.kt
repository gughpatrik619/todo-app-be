package com.example.todoapp.repository

import com.example.todoapp.model.TodoEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TodoRepository : JpaRepository<TodoEntity, Long> {

    fun findByUserUsername(username: String): List<TodoEntity>

    fun findByUserUsernameAndId(username: String, id: Long): Optional<TodoEntity>
}