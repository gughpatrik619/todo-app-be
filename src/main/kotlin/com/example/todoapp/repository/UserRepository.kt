package com.example.todoapp.repository

import com.example.todoapp.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByUsername(username: String): Optional<User>

    fun findByEmail(email: String): Optional<User>

    fun existsByUsername(username: String): Boolean

    fun existsByEmail(email: String): Boolean

    fun deleteByUsername(username: String)
}