package com.example.todoapp.service

import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.payload.request.RefreshTokenRequest
import com.example.todoapp.payload.response.AuthenticationResponse
import com.example.todoapp.repository.RefreshTokenRepository
import com.example.todoapp.repository.UserRepository
import com.example.todoapp.security.JwtProvider
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.Instant
import javax.transaction.Transactional

@Service
class RefreshTokenService(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
    private val userRepository: UserRepository
) {
    fun refreshToken(refreshTokenRequest: RefreshTokenRequest): ResponseEntity<AuthenticationResponse> {
        validateRefreshToken(refreshTokenRequest.refreshToken)
        val token = jwtProvider.generateJwtTokenWithUsername(refreshTokenRequest.username)
        val user = userRepository.findByUsername(refreshTokenRequest.username)
            .orElseThrow { RuntimeException("User not found with username ${refreshTokenRequest.username}") }

        return ResponseEntity.ok(
            AuthenticationResponse(
                jwtToken = token,
                id = user.id,
                type = null,
                username = user.username,
                email = user.email,
                roles = user.roles.map { it.name.name },
                refreshToken = refreshTokenRequest.refreshToken,
                expiresAt = Instant.now().plusMillis(jwtProvider.jwtExpirationMs)
            )
        )
    }

    private fun validateRefreshToken(token: String) {
        refreshTokenRepository.findByToken(token).orElseThrow { ResourceNotFoundException("Invalid refresh token") }
    }

    @Transactional
    fun deleteRefreshToken(token: String) = refreshTokenRepository.deleteByToken(token)
}