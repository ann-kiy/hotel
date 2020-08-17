package com.project.hotel.handler

import com.project.hotel.model.users.User
import com.project.hotel.service.UserService
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.notFound
import org.springframework.web.reactive.function.server.ServerResponse.ok


@Component
class UserHandler(private val userService: UserService) {
    @FlowPreview
    suspend fun addUser(request: ServerRequest): ServerResponse {
        var user=request.awaitBody<User>()
        user= userService.addUser(user)!!
        return when {
           user!=null ->ok().json().bodyValueAndAwait(user)
            else->notFound().buildAndAwait()
        }
    }
    suspend fun addPhoto(request: ServerRequest): ServerResponse{
        val id= request.pathVariable("id")
        val file=request.awaitMultipartData().get("photo")?.get(0) as FilePart
        return when {
            id.isNotEmpty() && file!=null -> {
                userService.addFile(userService.findById(id).awaitFirst(), file)
                ok().buildAndAwait()}
            else->notFound().buildAndAwait()
        }
    }

}