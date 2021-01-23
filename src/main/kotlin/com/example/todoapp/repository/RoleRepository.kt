package com.example.todoapp.repository

import com.example.todoapp.model.ERole
import com.example.todoapp.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<RoleEntity, Long> {

    fun findByName(name: ERole): Optional<RoleEntity>
}