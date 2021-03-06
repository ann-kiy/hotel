package com.project.hotel.routers

import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import com.mongodb.MongoWriteException
import com.project.hotel.HotelApplication
import com.project.hotel.model.dto.AuthUser
import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.User
import com.project.hotel.service.UserService
import com.project.hotel.service.security.AuthService
import com.project.hotel.service.security.SessionJWTService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.reactive.function.server.*
import java.util.logging.Logger


@Configuration
class UsersRouter(private val userService: UserService, private val authService: AuthService, private val sessionJWTService: SessionJWTService) {
    @Bean
    fun userRouter() = coRouter {
        "/api".nest {
//            POST("/user") { request ->
//                try {
//                    val user = request.awaitBodyOrNull<NewUser>()
//                    if (user != null) {
//                        userService.addUser(user)?.let {
//                            userService.sendMessage(it)
//                        }
//                        ServerResponse.ok().buildAndAwait()
//                    } else {
//                        ServerResponse.badRequest().buildAndAwait()
//                    }
//                } catch (e: org.springframework.dao.DuplicateKeyException) {
//                    ServerResponse.badRequest().buildAndAwait()
//                } catch (e: Exception) {
//                    Logger.getLogger(HotelApplication::class.java.name).warning(e.toString())
//                    ServerResponse.badRequest().buildAndAwait()
//                }
//            }
            POST("/auth") { request ->
                val user = request.awaitBodyOrNull<AuthUser>()
                if (user != null) {
                    val jwt = authService.auth(user)
                    if (jwt != null) {
                        val authCookie = ResponseCookie.fromClientResponse("X-Auth", jwt.accessToken)
                                .maxAge(3600)
                                .httpOnly(true)
                                .path("/")
                                .secure(false)
                                .build()
                        ServerResponse.ok().cookie(authCookie).bodyValueAndAwait(jwt)
                    } else {
                        ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
                    }
                } else {
                    ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
                }
            }
            POST("/user") {
                    ServerResponse.status(HttpStatus.ACCEPTED).bodyValueAndAwait("fghj")            }
            POST("/refresh") { request ->
                val token = request.awaitBody<String>()
                val session = authService.refresh(token)
                if (session != null) {
                    val authCookie = ResponseCookie.fromClientResponse("X-Auth", session.accessToken)
                            .maxAge(3600)
                            .httpOnly(true)
                            .path("/")
                            .secure(false)
                            .build()
                    ServerResponse.ok().cookie(authCookie).bodyValueAndAwait(session)
                } else {
                    ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
                }
            }
        }
        POST("/logout") { request ->
            request.cookies().remove("X-Auth")
            ServerResponse.status(HttpStatus.UNAUTHORIZED).buildAndAwait()
        }

        POST("/activate/{code}") {
            val code = it.pathVariable("code")
            if (userService.activateUser(code) != null) {
                ServerResponse.ok().buildAndAwait()
            } else
                ServerResponse.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).buildAndAwait()
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
