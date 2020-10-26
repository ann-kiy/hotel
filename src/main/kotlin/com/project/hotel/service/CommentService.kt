package com.project.hotel.service

import com.project.hotel.model.Comment
import com.project.hotel.repository.CommentRepo
import kotlinx.coroutines.reactive.awaitFirstOrNull
import reactor.core.publisher.Flux

interface CommentService {
    val commentRepo: CommentRepo

    suspend fun add(comment: Comment): Comment?
    suspend fun update(commentId: String, newComment: Comment): Comment?
    suspend fun delete(commentId: String): Boolean
    suspend fun getByRecipient(recipientId: String): Flux<Comment>? =
            commentRepo.findAllByRecipientIdAndState(recipientId, true)

    suspend fun getByAuth(authId: String): Flux<Comment>? =
            commentRepo.findAllByAuthIdAndState(authId, true)
}