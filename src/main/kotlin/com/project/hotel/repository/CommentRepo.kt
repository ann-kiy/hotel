package com.project.hotel.repository

import com.project.hotel.model.Comment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface CommentRepo:ReactiveMongoRepository<Comment, String> {
    suspend fun findByRecipientIdAndState(recipientId:String, state:Boolean): Flux<Comment>
    suspend fun findByAuthIdAndState(authId:String, state: Boolean): Flux<Comment>
}