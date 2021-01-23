package com.example.todoapp.service

import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.model.ActivationEmail
import com.example.todoapp.model.ERole
import com.example.todoapp.model.UserEntity
import com.example.todoapp.model.VerificationTokenEntity
import com.example.todoapp.payload.request.SignupRequest
import com.example.todoapp.payload.response.MessageResponse
import com.example.todoapp.repository.RoleRepository
import com.example.todoapp.repository.UserRepository
import com.example.todoapp.security.JwtProvider
import com.example.todoapp.util.Constants
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class SignupService(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val mailService: MailService,
    private val jwtProvider: JwtProvider,

    @Value("\${todo.app.verification_token.expiration_ms}")
    val verificationTokenExpirationMs: Long
) {
    fun signup(signupRequest: SignupRequest): ResponseEntity<MessageResponse> {
        if (userRepository.existsByUsername(signupRequest.username)) {
            return ResponseEntity.badRequest().body(MessageResponse("Username is already taken"))
        }
        if (userRepository.existsByEmail(signupRequest.email)) {
            return ResponseEntity.badRequest().body(MessageResponse("Email is already in use"))
        }

        val user = UserEntity(
            username = signupRequest.username,
            email = signupRequest.email,
            password = passwordEncoder.encode(signupRequest.password),
            enabled = false,
            roles = mutableListOf(
                roleRepository.findByName(ERole.ROLE_USER).orElseThrow { ResourceNotFoundException("Role not found") }
            ),
            todos = mutableListOf(),
            created = null
        )

        val token = UUID.randomUUID().toString()
        val expiry = Instant.now().plusMillis(verificationTokenExpirationMs)
        user.addVerificationToken(
            VerificationTokenEntity(
                token = token,
                expiresAt = expiry
            )
        )
        userRepository.save(user)
        mailService.sendActivationEmail(
            ActivationEmail(
                recipient = user.email,
                subject = Constants.ACTIVATION_EMAIL_SUBJECT,
                activationUrl = "${Constants.ACTIVATION_EMAIL_URI}$token"
            )
        )
        return ResponseEntity.ok(MessageResponse("Activation email sent. Expires at: $expiry"))
    }
}