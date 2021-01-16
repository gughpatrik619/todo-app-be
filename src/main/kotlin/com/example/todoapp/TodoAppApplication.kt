package com.example.todoapp

import com.example.todoapp.config.SwaggerConfiguration
import com.example.todoapp.model.ERole
import com.example.todoapp.model.Role
import com.example.todoapp.model.User
import com.example.todoapp.repository.RoleRepository
import com.example.todoapp.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.crypto.password.PasswordEncoder

@EnableAsync
@SpringBootApplication
@Import(SwaggerConfiguration::class)
class TodoAppApplication {

    @Bean
    fun init(
        userRepository: UserRepository,
        roleRepository: RoleRepository,
        passwordEncoder: PasswordEncoder
    ) = CommandLineRunner {
        roleRepository.save(Role(id = null, name = ERole.ROLE_USER))
        roleRepository.save(Role(id = null, name = ERole.ROLE_MODERATOR))
        roleRepository.save(Role(id = null, name = ERole.ROLE_ADMIN))

        userRepository.save(User.dummyUser(passwordEncoder, roleRepository))
        userRepository.save(User.dummyAdmin(passwordEncoder, roleRepository))
    }
}

fun main(args: Array<String>) {
    runApplication<TodoAppApplication>(*args)
}
