package com.example.todoapp.model

import com.example.todoapp.exception.ResourceNotFoundException
import com.example.todoapp.repository.RoleRepository
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//    @Column(columnDefinition = "BINARY(16)")                         // MySQL
    @GeneratedValue(strategy = GenerationType.AUTO)            // Postgres
    var id: UUID? = null,

    @Column(unique = true)
    var username: String?,

    var password: String?,

    var email: String?,

    @CreationTimestamp
    var created: Instant?,

    var enabled: Boolean,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: MutableList<RoleEntity>,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    var todos: MutableList<TodoEntity>,

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    var verificationToken: VerificationTokenEntity? = null
) {
    fun addVerificationToken(token: VerificationTokenEntity) {
        this.verificationToken = token
        token.user = this
    }

    fun addTodo(todo: TodoEntity) {
        todos.add(todo)
        todo.user = this
    }

    fun removeTodo(todo: TodoEntity) {
        todos.remove(todo)
        todo.user = null
    }

    companion object {
        fun dummyUser(passwordEncoder: PasswordEncoder, roleRepository: RoleRepository) =
            UserEntity(
                id = null,
                username = "user",
                password = passwordEncoder.encode("password"),
                email = "user@domain.com",
                enabled = true,
                verificationToken = null,
                created = null,
                roles = mutableListOf(
                    roleRepository.findByName(ERole.ROLE_USER).orElseThrow {
                        ResourceNotFoundException("Error: role not found")
                    }
                ),
                todos = mutableListOf()
            ).apply { repeat(20) { this.addTodo(TodoEntity.random()) } }

        fun dummyAdmin(passwordEncoder: PasswordEncoder, roleRepository: RoleRepository) =
            UserEntity(
                id = null,
                username = "admin",
                password = passwordEncoder.encode("password"),
                email = "admin@domain.com",
                enabled = true,
                verificationToken = null,
                created = null,
                roles = mutableListOf(
                    roleRepository.findByName(ERole.ROLE_USER).orElseThrow {
                        ResourceNotFoundException("Error: role not found")
                    },
                    roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow {
                        ResourceNotFoundException("Error: role not found")
                    },
                    roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow {
                        ResourceNotFoundException("Error: role not found")
                    }
                ),
                todos = mutableListOf()
            ).apply { repeat(20) { this.addTodo(TodoEntity.random()) } }
    }
}