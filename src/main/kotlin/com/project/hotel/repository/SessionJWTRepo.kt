package com.project.hotel.repository

import com.project.hotel.model.SessionJwt
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface SessionJWTRepo: ReactiveMongoRepository<SessionJwt, String> {
   suspend fun findByRefreshToken(refreshToken: String): Mono<SessionJwt>
}