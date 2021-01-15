package com.example.todoapp.restcontroller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping(value = ["api"])
class HelloWorldController {

    @GetMapping(value = ["hello"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun hello() = "Hello World"
}