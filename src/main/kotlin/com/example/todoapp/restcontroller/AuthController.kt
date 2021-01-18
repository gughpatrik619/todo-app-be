package com.example.todoapp.restcontroller

import com.example.todoapp.payload.request.LoginRequest
import com.example.todoapp.payload.request.RefreshTokenRequest
import com.example.todoapp.payload.request.SignupRequest
import com.example.todoapp.service.AuthService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping(value = ["auth"])
class AuthController(private val authService: AuthService) {

    @PostMapping(value = ["login"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@Valid @RequestBody loginRequest: LoginRequest) = authService.login(loginRequest)

    @PostMapping(value = ["logout"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun logout(@Valid @RequestBody refreshTokenRequest: RefreshTokenRequest) = authService.logout(refreshTokenRequest)

    @PostMapping(value = ["signup"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signup(@Valid @RequestBody signupRequest: SignupRequest) = authService.signup(signupRequest)

    @GetMapping(value = ["accountVerification/{token}"])
    fun verifyAccount(@PathVariable token: String) = authService.verifyAccount(token)

    @PostMapping(value = ["refreshToken"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun refreshToken(@Valid @RequestBody refreshTokenRequest: RefreshTokenRequest) =
        authService.refreshToken(refreshTokenRequest)
}