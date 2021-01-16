package com.example.todoapp.security

import com.example.todoapp.model.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtUtils(
    @Value("\${todo.app.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${todo.app.jwt.expiration_ms}")
    private val jwtExpirationMs: Int
) {
    fun generateJwtToken(authentication: Authentication): String =
        Jwts.builder()
            .setSubject((authentication.principal as UserDetailsImpl).username)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + jwtExpirationMs))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    fun getUserNameFromJwtToken(token: String?): String =
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject

    fun validateJwtToken(authToken: String) =
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            true
        } catch (e: Exception) {
            false
        }

}