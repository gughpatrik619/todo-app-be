package com.example.todoapp.restcontroller

import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
class HelloWorldController {

    @GetMapping(value = ["hello"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun hello() = "Hello world!"

//    @PreAuthorize(value = "#username == principal.username")
//    @GetMapping(value = ["{username}/roles"], produces = [MediaType.TEXT_PLAIN_VALUE])
//    fun getRolesForUser(@PathVariable username: String) = "roles: a,b,c for user: $username"
}