package com.project.hotel.routers

import com.project.hotel.model.Comment
import com.project.hotel.service.CommentAnimalService
import com.project.hotel.service.CommentUserService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux

@Configuration
class CommentRouter(val commentAnimalService: CommentAnimalService, val commentUserService: CommentUserService) {
    @Bean
    fun commentsRouter() = coRouter {
        "api/comment".nest {
            POST("/user") { request ->
                var comment = request.awaitBodyOrNull<Comment>()
                if (comment != null) {
                    if (commentUserService.add(comment) != null)
                        ServerResponse.ok().buildAndAwait()
                    else
                        ServerResponse.badRequest().buildAndAwait()
                } else {
                    ServerResponse.badRequest().buildAndAwait()
                }
            }
            POST("/animal") { request ->
                var comment = request.awaitBodyOrNull<Comment>()
                if (comment != null) {
                    if (commentAnimalService.add(comment) != null)
                        ServerResponse.ok().buildAndAwait()
                    else
                        ServerResponse.badRequest().buildAndAwait()
                } else {
                    ServerResponse.badRequest().buildAndAwait()
                }
            }
            PUT("/user/{id}") { request ->
                val comment = request.awaitBodyOrNull<Comment>()
                val idOldComment = request.pathVariable("id")
                if (comment != null) {
                    if (commentUserService.update(idOldComment, comment) != null)
                        ServerResponse.ok().buildAndAwait()
                    else
                        ServerResponse.badRequest().buildAndAwait()
                } else {
                    ServerResponse.badRequest().buildAndAwait()
                }
            }
            PUT("/animal/{id}") { request ->
                val comment = request.awaitBodyOrNull<Comment>()
                val idOldComment = request.pathVariable("id")
                if (comment != null) {
                    if (commentAnimalService.update(idOldComment, comment) != null)
                        ServerResponse.ok().buildAndAwait()
                    else
                        ServerResponse.badRequest().buildAndAwait()
                } else {
                    ServerResponse.badRequest().buildAndAwait()
                }
            }
            DELETE("/user/{id}") { request ->
                val idComment = request.pathVariable("id")
                if (commentUserService.delete(idComment))
                    ServerResponse.ok().buildAndAwait()
                else
                    ServerResponse.badRequest().buildAndAwait()
            }
            DELETE("/animal/{id}") { request ->
                val idComment = request.pathVariable("id")
                if (commentAnimalService.delete(idComment))
                    ServerResponse.ok().buildAndAwait()
                else
                    ServerResponse.badRequest().buildAndAwait()
            }
            GET("/recipient/{id}") { request ->
                val id = request.pathVariable("id")
                val comments = commentUserService.getByRecipient(id)
                if (comments != null)
                    ServerResponse.ok()
                            .bodyValueAndAwait(comments.collectList().awaitFirst())
                else
                    ServerResponse.status(HttpStatus.NO_CONTENT).buildAndAwait()
            }
            GET("/auth/{id}") { request ->
                val id = request.pathVariable("id")
                val comments = commentUserService.getByAuth(id)
                if (comments != null)
                    ServerResponse.ok()
                            .bodyValueAndAwait(comments.collectList().awaitFirst())
                else
                    ServerResponse.status(HttpStatus.NO_CONTENT).buildAndAwait()
            }
        }
    }
}