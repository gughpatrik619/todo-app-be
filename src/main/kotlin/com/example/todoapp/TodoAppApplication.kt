package com.example.todoapp

import com.example.todoapp.config.SwaggerConfiguration
import com.example.todoapp.model.ERole
import com.example.todoapp.model.RoleEntity
import com.example.todoapp.model.UserEntity
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
        roleRepository.save(RoleEntity(id = null, name = ERole.ROLE_USER))
        roleRepository.save(RoleEntity(id = null, name = ERole.ROLE_MODERATOR))
        roleRepository.save(RoleEntity(id = null, name = ERole.ROLE_ADMIN))

        userRepository.save(UserEntity.dummyUser(passwordEncoder, roleRepository))
        userRepository.save(UserEntity.dummyAdmin(passwordEncoder, roleRepository))
    }
}

fun main(args: Array<String>) {
    runApplication<TodoAppApplication>(*args)
}
