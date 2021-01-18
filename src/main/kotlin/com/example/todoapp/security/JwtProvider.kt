package com.example.todoapp.security

import com.example.todoapp.model.UserDetailsImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtProvider(
    @Value("\${todo.app.jwt.secret}")
    val jwtSecret: String,

    @Value("\${todo.app.jwt.expiration_ms}")
    val jwtExpirationMs: Long
) {
    fun generateJwtToken(authentication: Authentication): String =
        Jwts.builder()
            .setSubject((authentication.principal as UserDetailsImpl).username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    fun generateJwtTokenWithUsername(username: String): String =
        Jwts.builder()
            .setSubject(username)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMs)))
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()

    fun getUserNameFromJwtToken(token: String): String =
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject

    fun validateJwtToken(authToken: String) =
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
            true
        } catch (e: Exception) {
            false
        }

}