package com.example.todoapp.repository

import com.example.todoapp.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<UserEntity, UUID> {

    fun findByUsername(username: String?): Optional<UserEntity>

    fun findByEmail(email: String?): Optional<UserEntity>

    fun existsByUsername(username: String?): Boolean

    fun existsByEmail(email: String?): Boolean
}