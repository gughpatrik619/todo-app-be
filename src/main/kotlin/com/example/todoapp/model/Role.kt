package com.example.todoapp.model

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Enumerated(EnumType.STRING)
    val name: ERole
)