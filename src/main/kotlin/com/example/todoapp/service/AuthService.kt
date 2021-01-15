package com.example.todoapp.service

import com.example.todoapp.payload.request.LoginRequest
import com.example.todoapp.payload.request.SignupRequest
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val signupService: SignupService,
    private val loginService: LoginService,
    private val accountVerificationService: AccountVerificationService,
) {
    fun signup(signupRequest: SignupRequest) = signupService.signup(signupRequest)

    fun verifyAccount(token: String) = accountVerificationService.verifyAccount(token)

    fun login(loginRequest: LoginRequest) = loginService.login(loginRequest)
}