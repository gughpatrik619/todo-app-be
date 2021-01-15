package com.example.todoapp.repository

import com.example.todoapp.model.ERole
import com.example.todoapp.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: ERole): Optional<Role>
}