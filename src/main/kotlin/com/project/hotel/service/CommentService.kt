package com.project.hotel.service

import com.project.hotel.model.Comment
import com.project.hotel.repository.CommentRepo
import kotlinx.coroutines.reactive.awaitFirstOrNull

interface CommentService {
    val commentRepo:CommentRepo

    suspend fun add(comment: Comment): Comment?
    suspend fun update(commentId:String, newComment: Comment):Comment?
    suspend fun delete(commentId: String)
    suspend fun getByRecipient(recipientId:String)=
            commentRepo.findByRecipientIdAndState(recipientId, true).awaitFirstOrNull()
    suspend fun getByAuth(authId:String)=
            commentRepo.findByAuthIdAndState(authId, true).awaitFirstOrNull()
    suspend fun getAll()=
            commentRepo.findAll().awaitFirstOrNull()
}