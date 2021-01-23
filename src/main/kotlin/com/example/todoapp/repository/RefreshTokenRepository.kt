package com.example.todoapp.repository

import com.example.todoapp.model.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long> {

    fun findByToken(token: String): Optional<RefreshTokenEntity>

    fun deleteByToken(token: String)
}