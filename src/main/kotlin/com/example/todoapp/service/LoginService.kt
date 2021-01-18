package com.example.todoapp.service

import com.example.todoapp.model.RefreshToken
import com.example.todoapp.model.UserDetailsImpl
import com.example.todoapp.payload.request.LoginRequest
import com.example.todoapp.payload.response.AuthenticationResponse
import com.example.todoapp.repository.RefreshTokenRepository
import com.example.todoapp.security.JwtProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class LoginService(
    private val authenticationManager: AuthenticationManager,
    private val jwtProvider: JwtProvider,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun login(loginRequest: LoginRequest): ResponseEntity<AuthenticationResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwtToken = jwtProvider.generateJwtToken(authentication)
        val refreshToken = refreshTokenRepository.save(
            RefreshToken(
                id = null,
                token = UUID.randomUUID().toString(),
                created = null
            )
        )

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.map { it.authority }

        return ResponseEntity.ok(
            AuthenticationResponse(
                jwtToken = jwtToken,
                id = userDetails.id,
                type = "Bearer token",
                username = userDetails.username,
                email = userDetails.email,
                roles = roles,
                refreshToken = refreshToken.token,
                expiresAt = Instant.now().plusMillis(jwtProvider.jwtExpirationMs)
            )
        )
    }
}