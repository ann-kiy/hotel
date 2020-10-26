package com.project.hotel.service

import com.project.hotel.model.Comment
import com.project.hotel.repository.CommentRepo
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CommentAnimalService(val animalService: AnimalService, override val commentRepo: CommentRepo) : CommentService {
    override suspend fun add(comment: Comment): Comment? {
        animalService.updateRating(comment.recipientId, comment.rating)
        return commentRepo.save(comment).awaitFirstOrNull()
    }

    override suspend fun update(commentId: String, newComment: Comment): Comment? {
        commentRepo.findById(commentId).awaitFirstOrNull()?.let {
            if (it.recipientId == newComment.recipientId) {
                if (!animalService.cancelRating(it.recipientId, it.rating).isNaN() &&
                        animalService.updateRating(newComment.recipientId, newComment.rating).isNaN()) {
                    newComment.updateDate = Instant.now()
                    return commentRepo.save(it.copy(
                            text = newComment.text,
                            rating = newComment.rating
                    )).awaitFirstOrNull()
                }
            }
        }
        return null
    }

    override suspend fun delete(commentId: String): Boolean {
        commentRepo.findById(commentId).awaitFirstOrNull()?.let {
            if (animalService.cancelRating(it.recipientId, it.rating).isNaN()) {
                it.state = false
                commentRepo.save(it).awaitFirstOrNull()
                return true
            }
        }
        return false
    }
}