package com.example.todoapp.restcontroller

import com.example.todoapp.model.dto.CreateTodoDto
import com.example.todoapp.service.TodoService
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping(value = ["api"])
class TodoController(private val todoService: TodoService) {

    @PreAuthorize(value = "#username == principal.username")
    @GetMapping(
        value = ["users/{username}/todos"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllByUsername(@PathVariable username: String) = todoService.getAllByUsername(username)

    @PreAuthorize(value = "#username == principal.username")
    @PostMapping(
        value = ["users/{username}/todos"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveByUsername(@PathVariable username: String, @RequestBody createTodoDto: CreateTodoDto) =
        todoService.saveByUsername(username, createTodoDto)
}