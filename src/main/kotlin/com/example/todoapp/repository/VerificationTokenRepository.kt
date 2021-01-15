package com.example.todoapp.repository

import com.example.todoapp.model.VerificationToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationTokenRepository : JpaRepository<VerificationToken, UUID> {

    fun findByToken(token: String): Optional<VerificationToken>
}