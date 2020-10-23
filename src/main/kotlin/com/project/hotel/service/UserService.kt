package com.project.hotel.service

import com.project.hotel.model.Comment
import com.project.hotel.model.dto.AuthUser
import com.project.hotel.model.dto.NewUser
import com.project.hotel.model.users.Role
import com.project.hotel.model.users.User
import com.project.hotel.repository.UserRepo
import com.project.hotel.service.security.JwtService
import com.project.hotel.util.DataState
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitLast
import org.springframework.http.codec.multipart.FilePart
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.*

@Service
class UserService(private val userRepo: UserRepo,
                  private val fileStorageService: FileStorageService,
                  private val mailService: MailService,
                    private val passwordEncoder: PasswordEncoder) {

    suspend fun addUser(userDTO: NewUser): User? {
            val user = userDTO.toUser()
             return userRepo.save(
                     user.copy(
                        password = passwordEncoder.encode(user.password),
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
                                active = DataState.NOT_ACTIVE)).awaitFirst()
                    }
                    sendMessage(user)
                    userRepo.save(user).awaitFirst()
                }
    }

    suspend fun activateUser(code: String): User? {
        return userRepo.findByActivateCode(code).awaitFirstOrNull()?.let {
            userRepo.save(it.copy(
                    activateCode = null,
                    active = DataState.ACTIVE)).awaitFirst()
        }

    }

    suspend fun deleteUser(id: String) {
        userRepo.findById(id).awaitFirstOrNull()?.let {
            userRepo.save(it.copy(active = DataState.NOT_ACTIVE))
        }
    }

    fun sendMessage(user: User) {
        val message = String.format("Hello, %s\n" +
                "Welcome to HotelAnimals. Please, visit next link: http://localhost:8080/activate/%s",
                user.name,
                user.activateCode)
        mailService.sent(user.email, "Activation code", message)
    }
    suspend fun updateRating(userId: String, rating: Float):Float{
        userRepo.findById(userId).awaitFirstOrNull()?.let{
            val amount = (it.rating * it.countComment) + rating
            it.rating = amount / (it.countComment + 1)
            it.countComment++
            userRepo.save(it)
            return it.rating
        }
        return Float.NaN
    }
    suspend fun cancelRating(userId: String, rating: Float):Float{
        userRepo.findById(userId).awaitFirstOrNull()?.let{
            if(it.rating>=rating && it.countComment>0) {
                it.rating -= rating
                it.countComment--
                userRepo.save(it)
                return it.rating
            }
        }
        return Float.NaN
    }
    suspend fun isActivate(email:String):DataState?=userRepo.findByEmail(email).awaitFirstOrNull()?.let{return it.active}
    fun findById(id: String): Mono<User> = userRepo.findById(id)
    suspend fun findByEmail(email: String): User? = userRepo.findByEmail(email).awaitFirstOrNull()
}