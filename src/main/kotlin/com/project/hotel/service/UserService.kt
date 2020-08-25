package com.project.hotel.service

import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.Role
import com.project.hotel.model.users.User
import com.project.hotel.repository.UserRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(private val userRepo: UserRepo, private val fileStorageService: FileStorageService, private val mailService: MailService) {

    suspend fun addUser(userDTO: NewUser): User? {
            val user = userDTO.toUser()
             return userRepo.save(
                     user.copy(
                        roles = setOf(Role.USER),
                        activateCode = UUID.randomUUID().toString())).awaitLast()

    }

    suspend fun addFile(user: User, file: FilePart) =
            userRepo.save(user.copy(avatar = fileStorageService.uploadFile(file))).awaitFirst()


    suspend fun updateUser(userDTO: User, userId: String): User? {
        return userRepo.findById(userId).awaitFirstOrNull()
                ?.let {
                    val isEmailChange = userDTO.email.isNotEmpty() && it.email != userDTO.email
                    val user = it.copy(
                            name = userDTO.name,
                            location = userDTO.location,
                            phone = userDTO.phone)
                    if (isEmailChange) {
                        userRepo.save(user.copy(
                                email = userDTO.email,
                                activateCode = UUID.randomUUID().toString(),
                                active = false)).awaitFirst()
                    }
                    sendMessage(user)
                    userRepo.save(user).awaitFirst()
                }
    }

    suspend fun activateUser(code: String): User? {
        return userRepo.findByActivateCode(code).awaitFirstOrNull()?.let {
            userRepo.save(it.copy(
                    activateCode = null,
                    active = true)).awaitFirst()
        }

    }

    suspend fun deleteUser(id: String) {
        userRepo.findById(id).awaitFirstOrNull()?.let {
            userRepo.save(it.copy(active = false))
        }
    }

    fun sendMessage(user: User) {
        val message = String.format("Hello, %s\n" +
                "Welcome to HotelAnimals. Please, visit next link: http://localhost:8080/activate/%s",
                user.name,
                user.activateCode)
        mailService.sent(user.email, "Activation code", message)
    }

    fun findById(id: String): Mono<User> = userRepo.findById(id)
    suspend fun findByEmail(email: String): User? = userRepo.findByEmail(email).awaitFirstOrNull()
}