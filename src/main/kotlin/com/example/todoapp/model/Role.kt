package com.example.todoapp.model

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @Enumerated(EnumType.STRING)
    var name: ERole
)