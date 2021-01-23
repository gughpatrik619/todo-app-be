package com.example.todoapp.service

import com.example.todoapp.exception.InvalidTokenException
import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.exception.TokenExpiredException
import com.example.todoapp.model.VerificationTokenEntity
import com.example.todoapp.payload.response.MessageResponse
import com.example.todoapp.repository.UserRepository
import com.example.todoapp.repository.VerificationTokenRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountVerificationService(
    private val verificationTokenRepository: VerificationTokenRepository,
    private val userRepository: UserRepository
) {
    @Transactional(noRollbackFor = [TokenExpiredException::class])
    fun verifyAccount(token: String): ResponseEntity<MessageResponse> {
        val verificationToken = verificationTokenRepository.findByToken(token)

        if (verificationToken.isEmpty) throw InvalidTokenException("Invalid token: $token")

        if (verificationToken.get().expired()) {
            userRepository.delete(verificationToken.get().user!!)
            throw TokenExpiredException("Token expired at: ${verificationToken.get().expiresAt}")
        }

        fetchUserAndEnable(verificationToken.get())
        return ResponseEntity.ok(MessageResponse("Account verified successfully"))
    }

    private fun fetchUserAndEnable(verificationToken: VerificationTokenEntity) {
        val username = verificationToken.user?.username
        val user = userRepository.findByUsername(username)
            .orElseThrow { ResourceNotFoundException("User not found with name: $username") }
        user.enabled = true
        userRepository.save(user)
    }
}