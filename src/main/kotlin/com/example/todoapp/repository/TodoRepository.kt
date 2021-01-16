package com.example.todoapp.repository

import com.example.todoapp.model.Todo
import org.springframework.data.jpa.repository.JpaRepository

interface TodoRepository : JpaRepository<Todo, Long> {

    fun findByUserUsername(username: String): List<Todo>
}