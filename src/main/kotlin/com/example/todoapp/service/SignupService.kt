package com.example.todoapp.service

import com.example.todoapp.model.ActivationEmail
import com.example.todoapp.model.ERole
import com.example.todoapp.model.User
import com.example.todoapp.model.VerificationToken
import com.example.todoapp.payload.request.SignupRequest
import com.example.todoapp.payload.response.MessageResponse
import com.example.todoapp.repository.RoleRepository
import com.example.todoapp.repository.UserRepository
import com.example.todoapp.util.Constants
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class SignupService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService
) {
    fun signup(signupRequest: SignupRequest): ResponseEntity<MessageResponse> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity.badRequest().body(MessageResponse("Error: Username is already taken!"))
        }
        if (userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity.badRequest().body(MessageResponse("Error: Email is already in use!"))
        }

        val user = User(
            username = signupRequest.username,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            enabled = false,
            roles = mutableSetOf(
                roleRepository.findByName(ERole.ROLE_USER).orElseThrow { RuntimeException("Error: role not found") }
            )
        )

        val token = UUID.randomUUID().toString()
        user.addVerificationToken(VerificationToken(token = token))
        userRepository.save(user)
        mailService.sendActivationEmail(
            ActivationEmail(
                recipient = user.email,
                subject = Constants.ACTIVATION_EMAIL_SUBJECT,
                activationUrl = "${Constants.ACTIVATION_EMAIL_URI}$token"
            )
        )
        return ResponseEntity.ok(MessageResponse("Activation email sent!"))
    }
}