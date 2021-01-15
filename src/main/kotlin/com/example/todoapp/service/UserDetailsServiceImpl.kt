package com.example.todoapp.service

import com.example.todoapp.model.User
import com.example.todoapp.model.UserDetailsImpl
import com.example.todoapp.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val userOptional: Optional<User> = userRepository.findByUsername(username)
        val user: User = userOptional.orElseThrow {
            UsernameNotFoundException("No user found with username $username")
        }
        return UserDetailsImpl.build(user)
    }
}