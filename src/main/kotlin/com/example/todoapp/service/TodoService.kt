package com.example.todoapp.service

import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.model.dto.CreateTodoDto
import com.example.todoapp.model.dto.TodoDto
import com.example.todoapp.repository.TodoRepository
import com.example.todoapp.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val userRepository: UserRepository
) {
    fun getAllByUsername(username: String) = todoRepository.findByUserUsername(username).map { it.toDto() }

    fun saveByUsername(username: String, createTodoDto: CreateTodoDto): TodoDto =
        userRepository.findByUsername(username).map {
            val todoEntity = createTodoDto.toEntity()
            it.addTodo(todoEntity)
            todoRepository.save(todoEntity).toDto()
        }.orElseThrow { ResourceNotFoundException("Not found user $username") }
}