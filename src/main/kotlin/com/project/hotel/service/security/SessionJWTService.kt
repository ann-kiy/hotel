package com.project.hotel.service.security

import com.project.hotel.model.SessionJwt
import com.project.hotel.model.users.User
import com.project.hotel.repository.SessionJWTRepo
import com.project.hotel.service.UserService
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.bson.types.ObjectId
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SessionJWTService(private val sessionJWTRepo: SessionJWTRepo,
                        private val jwtService: JwtService,
                        private val userService: UserService ) {
    suspend fun createSession(userId: ObjectId, token: String)=
        sessionJWTRepo.save(SessionJwt(userId =userId, refreshToken = token))

    suspend fun refreshSession(token:String): Mono<SessionJwt>? {
        val session=sessionJWTRepo.findByRefreshToken(token).awaitFirstOrNull()
        val user=userService.findById(session?.userId.toString()).awaitFirst()
        if(session!=null)
            return createSession(session.userId, jwtService.createRefreshJwt(user.email))
        return null
    }

}