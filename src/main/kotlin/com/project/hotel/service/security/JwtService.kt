package com.project.hotel.service.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.time.Duration
import java.time.Instant
import java.util.*

@Service
class JwtService {
    val keyAccessPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)
    val keyRefrechPair: KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256)

    fun createAcсessJwt(data: String): String {
        return Jwts.builder()
                .signWith(keyAccessPair.private, SignatureAlgorithm.RS256)
                .setSubject(data)
                .setIssuer("identity")
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
                .setIssuedAt(Date.from(Instant.now()))
                .compact()
    }
    fun createRefreshJwt(data: String): String {
        return Jwts.builder()
                .signWith(keyRefrechPair.private, SignatureAlgorithm.RS256)
                .setSubject(data+Instant.now().toString())
                .setIssuer("identity")
                .setExpiration(Date.from(Instant.now().plus(Duration.ofMinutes(30))))
                .setIssuedAt(Date.from(Instant.now()))
                .compact()
    }
    fun validateJwt(jwt: String, key: KeyPair): Jws<Claims> {
        return Jwts.parserBuilder()
                .setSigningKey(key.public)
                .build()
                .parseClaimsJws(jwt)
    }
}