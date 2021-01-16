package com.example.todoapp.service

import com.example.todoapp.model.UserDetailsImpl
import com.example.todoapp.payload.request.LoginRequest
import com.example.todoapp.payload.response.JwtResponse
import com.example.todoapp.security.JwtUtils
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {
    fun login(loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.username, loginRequest.password)
        )

        SecurityContextHolder.getContext().authentication = authentication
        val jwtToken = jwtUtils.generateJwtToken(authentication)

        val userDetails = authentication.principal as UserDetailsImpl
        val roles = userDetails.authorities.map { it.authority }

        return ResponseEntity.ok(
            JwtResponse(
                token = jwtToken,
                id = userDetails.id,
                type = "Bearer token",
                username = userDetails.username,
                email = userDetails.email,
                roles = roles,
                todos = userDetails.todos
            )
        )
    }
}