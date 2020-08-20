package com.project.hotel.service

import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.Role
import com.project.hotel.model.users.User
import com.project.hotel.repository.UserRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(private val userRepo: UserRepo, private val fileStorageService: FileStorageService) {

    suspend fun addUser(userDTO: NewUser): User? {
        return userRepo.findByEmail(userDTO.email).awaitFirstOrNull()?.let {
            val user = userDTO.toUser()
            user.roles = Collections.singleton(Role.USER)
            user.activateCode = UUID.randomUUID().toString()
            //sendMessage(userFDB)
            userRepo.save(user).awaitFirst()
        }
    }

    suspend fun addFile(user: User, file: FilePart) {
        user.img = fileStorageService.store(file)
        userRepo.save(user).awaitFirst()
    }

    suspend fun updateUser(userDTO: User, userId: String): User? {
        return userRepo.findById(userId).awaitFirstOrNull()
                ?.let {
                    it.name = userDTO.name
                    it.locale = userDTO.locale
                    it.phone = userDTO.phone
                    val isEmailChange = (it.email != userDTO.email) && userDTO.email.isNotEmpty()
                    if (isEmailChange) {
                        //sendMessage(userFDB)
                        userRepo.save(it.copy(email = userDTO.email, activateCode = UUID.randomUUID().toString(), active = false)).awaitFirst()
                    }
                    userRepo.save(it).awaitFirst()
                }
    }

    suspend fun activateUser(code: String): User? {
        return userRepo.findByActivateCode(code).awaitFirstOrNull()?.let {
            it.activateCode = String()
            userRepo.save(it.copy(active = true)).awaitFirst()
        }

    }

    suspend fun deleteUser(id: String) {
        userRepo.findById(id).awaitFirstOrNull()?.let {
            userRepo.save(it.copy(active = false))
        }
    }

    suspend fun findById(id: String): Mono<User> = userRepo.findById(id)
    suspend fun findByEmail(email: String): User? = userRepo.findByEmail(email).awaitFirstOrNull()
}