package com.example.todoapp.restcontroller

import com.example.todoapp.payload.response.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping(value = ["auth/test"])
class AuthTestController {

    @GetMapping(value = ["all"])
    fun allAccess() = ResponseEntity.ok(MessageResponse("Public content"))

    @GetMapping(value = ["user"])
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    fun userAccess() = ResponseEntity.ok(MessageResponse("User content"))

    @GetMapping(value = ["mod"])
    @PreAuthorize("hasRole('MODERATOR')")
    fun moderatorAccess() = ResponseEntity.ok(MessageResponse("Moderator content"))

    @GetMapping(value = ["admin"])
    @PreAuthorize("hasRole('ADMIN')")
    fun adminAccess() = ResponseEntity.ok(MessageResponse("Admin content"))
}