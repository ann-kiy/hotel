package com.project.hotel.service

import com.project.hotel.model.Comment
import com.project.hotel.repository.CommentRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CommentUserService(override val commentRepo: CommentRepo, val userService: UserService):CommentService {
    override suspend fun add(comment: Comment): Comment? {
       userService.updateRating(comment.recipientId, comment.rating)
       return commentRepo.save(comment).awaitFirstOrNull()
    }

    override suspend fun update(commentId:String, newComment: Comment):Comment? {
        commentRepo.findById(commentId).awaitFirstOrNull()?.let {
            if(it.recipientId == newComment.recipientId) {
                userService.cancelRating(it.recipientId, it.rating)
                userService.updateRating(newComment.recipientId, newComment.rating)
                newComment.updateDate = Instant.now()
                return commentRepo.save(it.copy(
                        text = newComment.text,
                        rating = newComment.rating
                )).awaitFirstOrNull()
            }
        }
        return null
    }
    override suspend fun delete(commentId: String) {
        commentRepo.findById(commentId).awaitFirstOrNull()?.let {
                userService.cancelRating(it.recipientId, it.rating)
                it.state = false
                commentRepo.save(it).awaitFirstOrNull()
        }
    }

}