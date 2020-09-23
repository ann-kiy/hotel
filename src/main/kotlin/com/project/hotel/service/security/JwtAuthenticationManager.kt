package com.project.hotel.service.security

import org.slf4j.LoggerFactory
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
                .map { jwtSigner.validateJwt(it.credentials as String, jwtSigner.keyAccessPair) }
                .onErrorResume {
                    loggerFactory.warn("Jwt validate error: $it")
                    Mono.empty()
                }
                .map { jws ->
                    UsernamePasswordAuthenticationToken(
                            jws.body.subject,
                            authentication.credentials as String,
                            mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
                    )
                }
    }
    companion object {
        private val loggerFactory = LoggerFactory.getLogger("AuthManager")
    }
}