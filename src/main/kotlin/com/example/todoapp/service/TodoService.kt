package com.example.todoapp.service

import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.model.dto.CreateTodoDto
import com.example.todoapp.model.dto.TodoDto
import com.example.todoapp.model.dto.UpdateTodoDto
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

    fun updateByUsername(username: String, id: Long, updateTodoDto: UpdateTodoDto): TodoDto {
        if (!userRepository.existsByUsername(username))
            throw ResourceNotFoundException("Not found user $username")

        val todoToUpdate = todoRepository.findByUserUsername(username).firstOrNull { it.id == id }
            ?: throw ResourceNotFoundException("Not found Todo with id $id")

        todoToUpdate.updateFromDto(updateTodoDto)
        return todoRepository.save(todoToUpdate).toDto()
    }

    fun deleteById(username: String, id: Long) {
        if (!userRepository.existsByUsername(username))
            throw ResourceNotFoundException("Not found user $username")

        val todoToUpdate = todoRepository.findByUserUsername(username).firstOrNull { it.id == id }
            ?: throw ResourceNotFoundException("Not found Todo with id $id")

        todoRepository.deleteById(id)
    }
}