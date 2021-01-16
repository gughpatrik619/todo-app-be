package com.example.todoapp.service

import com.example.todoapp.model.VerificationToken
import com.example.todoapp.payload.response.MessageResponse
import com.example.todoapp.repository.UserRepository
import com.example.todoapp.repository.VerificationTokenRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AccountVerificationService(
    private val verificationTokenRepository: VerificationTokenRepository,
    private val userRepository: UserRepository
) {
    fun verifyAccount(token: String): ResponseEntity<MessageResponse> {
        val verificationToken = verificationTokenRepository.findByToken(token)
        fetchUserAndEnable(verificationToken.orElseThrow { RuntimeException("Invalid token") })
        return ResponseEntity.ok(MessageResponse("Account verified successfully"))
    }

    private fun fetchUserAndEnable(verificationToken: VerificationToken) {
        val username = verificationToken.user?.username
        val user = userRepository.findByUsername(username)
            .orElseThrow { RuntimeException("User not found with name: $username") }
        user.enabled = true
        userRepository.save(user)
    }
}