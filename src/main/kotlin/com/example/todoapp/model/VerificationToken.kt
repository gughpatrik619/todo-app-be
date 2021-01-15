package com.example.todoapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tokens")
data class VerificationToken(
    @Id
    val id: UUID,

    val token: String,

    @OneToOne
    @MapsId
    @JsonIgnore
    var user: User,

    val expiryDate: Instant
)