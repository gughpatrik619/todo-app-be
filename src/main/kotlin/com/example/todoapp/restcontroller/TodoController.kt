package com.example.todoapp.restcontroller

import com.example.todoapp.repository.TodoRepository
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping(value = ["api"])
class TodoController(private val todoRepository: TodoRepository) {

    @PreAuthorize(value = "#username == principal.username")
    @GetMapping(value = ["users/{username}/todos"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getByUsername(@PathVariable username: String) = todoRepository.findByUserUsername(username)
}