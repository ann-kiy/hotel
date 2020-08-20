package com.project.hotel.routers

import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.User
import com.project.hotel.service.UserService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.reactive.function.server.*

@Configuration
class UserRouter(private val userService: UserService) {
    @Bean
    fun userRouter() = coRouter {
        "/api/users".nest {
            POST("/") { request ->
                val user = request.bodyToMono(NewUser::class.java).awaitFirstOrNull()
                if (user != null) {
                    userService.addUser(user)
                    ServerResponse.ok().bodyValueAndAwait(user)
                } else
                    ServerResponse.badRequest().buildAndAwait()
            }
            POST("/file/{id}") {
                val id = it.pathVariable("id")
                val file = it.awaitMultipartData().get("photo")?.get(0) as FilePart
                if (id.isNotEmpty() && file != null) {
                    userService.addFile(userService.findById(id).awaitFirst(), file)
                    ServerResponse.ok().buildAndAwait()
                } else ServerResponse.notFound().buildAndAwait()
            }
        }
    }
}