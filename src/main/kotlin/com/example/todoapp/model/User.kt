package com.example.todoapp.model

import com.example.todoapp.repository.RoleRepository
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")                         // MySQL
    //    @GeneratedValue(strategy = GenerationType.AUTO)            // Postgres
    val id: UUID?,

    @NotBlank(message = "Username is required")
    @Column(unique = true)
    val username: String?,

    @NotBlank(message = "Password is required")
    val password: String?,

    @Email
    @NotEmpty(message = "Email is required")
    val email: String?,

    @CreationTimestamp
    val created: Date?,

    val enabled: Boolean,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableSet<Role>,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    var verificationToken: VerificationToken?
) {
    fun addVerificationToken(token: VerificationToken) {
        this.verificationToken = token
        token.user = this
    }

    companion object {
        fun dummyUser(passwordEncoder: PasswordEncoder, roleRepository: RoleRepository) =
            User(
                id = null,
                username = "user",
                password = passwordEncoder.encode("password"),
                email = "user@domain.com",
                enabled = true,
                verificationToken = null,
                created = Date.from(Instant.now()),
                roles = mutableSetOf(
                    roleRepository.findByName(ERole.ROLE_USER).orElseThrow {
                        RuntimeException("Error: role not found")
                    }
                )
            )

        fun dummyAdmin(passwordEncoder: PasswordEncoder, roleRepository: RoleRepository) =
            User(
                id = null,
                username = "admin",
                password = passwordEncoder.encode("password"),
                email = "admin@domain.com",
                enabled = true,
                verificationToken = null,
                created = Date.from(Instant.now()),
                roles = mutableSetOf(
                    roleRepository.findByName(ERole.ROLE_USER).orElseThrow {
                        RuntimeException("Error: role not found")
                    },
                    roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow {
                        RuntimeException("Error: role not found")
                    },
                    roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow {
                        RuntimeException("Error: role not found")
                    }
                )
            )
    }
}