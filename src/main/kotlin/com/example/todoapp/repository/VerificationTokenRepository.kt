package com.example.todoapp.repository

import com.example.todoapp.model.VerificationTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VerificationTokenRepository : JpaRepository<VerificationTokenEntity, UUID> {

    fun findByToken(token: String): Optional<VerificationTokenEntity>
}