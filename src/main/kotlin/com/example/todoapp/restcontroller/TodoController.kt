package com.example.todoapp.restcontroller

import com.example.todoapp.model.dto.CreateTodoDto
import com.example.todoapp.model.dto.UpdateTodoDto
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
    fun getForUser(@PathVariable username: String) = todoService.getAllForUser(username)

    @PreAuthorize(value = "#username == principal.username")
    @GetMapping(
        value = ["users/{username}/todos/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getByIdForUser(@PathVariable username: String, @PathVariable id: Long) =
        todoService.getByIdForUser(username, id)

    @PreAuthorize(value = "#username == principal.username")
    @PostMapping(
        value = ["users/{username}/todos"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveForUser(@PathVariable username: String, @RequestBody createTodoDto: CreateTodoDto) =
        todoService.saveForUser(username, createTodoDto)

    @PreAuthorize(value = "#username == principal.username")
    @PutMapping(
        value = ["users/{username}/todos/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateForUser(
        @PathVariable username: String,
        @PathVariable id: Long,
        @RequestBody updateTodoDto: UpdateTodoDto
    ) = todoService.updateForUser(username, id, updateTodoDto)

    @PreAuthorize(value = "#username == principal.username")
    @DeleteMapping(value = ["users/{username}/todos/{id}"])
    fun deleteByIdForUser(@PathVariable username: String, @PathVariable id: Long) = todoService.deleteByIdForUser(username, id)
}