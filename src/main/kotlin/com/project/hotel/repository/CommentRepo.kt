package com.project.hotel.repository

import com.project.hotel.model.Comment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface CommentRepo : ReactiveMongoRepository<Comment, String> {
    suspend fun findAllByRecipientIdAndState(recipientId: String, state: Boolean): Flux<Comment>?
    suspend fun findAllByAuthIdAndState(authId: String, state: Boolean): Flux<Comment>?
}