package com.project.hotel.service.security

import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(private val jwtSigner: JwtService) : ReactiveAuthenticationManager {
    @Override
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.just(authentication)
                .map { jwtSigner.validateJwt(it.principal as String, jwtSigner.keyAccessPair) }
                .onErrorResume { Mono.empty() }
                .map { jws ->
                    UsernamePasswordAuthenticationToken(
                            jws.body.subject,
                            authentication.credentials as String,
                            mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
                    )
                }
    }
}