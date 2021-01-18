package com.example.todoapp.service

import com.example.todoapp.payload.request.LoginRequest
import com.example.todoapp.payload.request.RefreshTokenRequest
import com.example.todoapp.payload.request.SignupRequest
import com.example.todoapp.payload.response.MessageResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val signupService: SignupService,
    private val loginService: LoginService,
    private val accountVerificationService: AccountVerificationService,
    private val refreshTokenService: RefreshTokenService
) {
    fun signup(signupRequest: SignupRequest) = signupService.signup(signupRequest)

    fun verifyAccount(token: String) = accountVerificationService.verifyAccount(token)

    fun login(loginRequest: LoginRequest) = loginService.login(loginRequest)

    fun refreshToken(refreshTokenRequest: RefreshTokenRequest) = refreshTokenService.refreshToken(refreshTokenRequest)

    fun logout(refreshTokenRequest: RefreshTokenRequest): ResponseEntity<MessageResponse> {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.refreshToken)
        return ResponseEntity.ok(MessageResponse("Refresh token deleted successfully"))
    }
}