package com.project.hotel.repository

import com.project.hotel.model.users.User
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepo : MongoRepo<User> {
    suspend fun findByEmail(email: String): Mono<User>
    suspend fun findByActivateCode(activateCode: String): Mono<User>
}