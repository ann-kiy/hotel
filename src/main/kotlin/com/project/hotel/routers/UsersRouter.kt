package com.project.hotel.routers

import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.project.hotel.HotelApplication
import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.User
import com.project.hotel.service.UserService
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.reactive.function.server.*
import java.util.logging.Logger


@Configuration
class UsersRouter(private val userService: UserService) {
    @Bean
    fun userRouter() = coRouter {
        "/api/users".nest {
            POST("/") { request ->
                try {
                    val user = request.awaitBody<NewUser>()
                    userService.addUser(user)?.let {
                        userService.sendMessage(it)
                    }
                    ServerResponse.ok().bodyValueAndAwait(user)
                } catch (e: org.springframework.dao.DuplicateKeyException) {
                    ServerResponse.badRequest().buildAndAwait()
                } catch (e: Exception) {
                    Logger.getLogger(HotelApplication::class.java.name).warning(e.toString())
                    ServerResponse.badRequest().buildAndAwait()
                }
            }
            POST("/file/{id}") {
                val id = it.pathVariable("id")
                val file = it.awaitMultipartData().get("photo")?.get(0) as FilePart
                if (id.isNotEmpty() && file != null) {
                    userService.addFile(userService.findById(id).awaitFirst(), file)
                    ServerResponse.ok().buildAndAwait()
                } else ServerResponse.notFound().buildAndAwait()
            }
            POST("/mail/{id}") {
                val id = it.pathVariable("id")
                if (id.isNotEmpty()) {
                    userService.sendMessage(userService.findById(id).awaitFirst())
                    ServerResponse.ok().buildAndAwait()
                } else ServerResponse.notFound().buildAndAwait()
            }
        }
    }
}