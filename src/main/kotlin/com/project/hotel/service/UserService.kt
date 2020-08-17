package com.project.hotel.service

import com.project.hotel.model.users.Role
import com.project.hotel.model.users.User
import com.project.hotel.repository.UserRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(private val userRepo:UserRepo, private val fileStorageService: FileStorageService) {

    suspend fun addUser(user: User): User?{
        if (userRepo.findByEmail(user.email).awaitFirstOrNull()!=null) return null
        user.roles= Collections.singleton(Role.user)
        user.lastVisit= LocalDateTime.now()
        user.activateCode=UUID.randomUUID().toString()

        userRepo.save(user).awaitFirst()
        return user
    }
    suspend fun addFile(user:User, file: FilePart):User{
        user.img=fileStorageService.store(file)
        userRepo.save(user).awaitFirst()
        return user
    }
    suspend fun updateUser(user:User, userId:String):User?{
        val userFDB=userRepo.findById(userId).awaitFirstOrNull()
        val isEmailChange=(userFDB?.email != user.email) && user.email.isNotEmpty()
        userFDB?.email=user.email
        userFDB?.locale=user.locale
        userFDB?.phone=user.phone

        if(isEmailChange){
            userFDB?.email=user.email
            userFDB?.activateCode=UUID.randomUUID().toString()
            //sendMessage(userFDB)
        }
        return userFDB
    }
    suspend fun activateUser(code:String):User?{
        val user=userRepo.findByActivateCode(code).awaitFirstOrNull()
        if(user!=null){
            user.activateCode= String()
            user.active=true
            userRepo.save(user)
        }
        return user
    }
    suspend fun findById(id:String): Mono<User> =userRepo.findById(id)
    suspend fun findByEmail(email:String): User? =userRepo.findByEmail(email).awaitFirstOrNull()
}