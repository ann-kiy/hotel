package com.project.hotel.service.security

import com.project.hotel.model.dto.AuthData
import com.project.hotel.model.dto.AuthUser
import com.project.hotel.repository.UserRepo
import com.project.hotel.service.UserService
import com.project.hotel.util.DataState
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(private val userService: UserService, private val passwordEncoder: PasswordEncoder, private val jwtService: JwtService, private val sessionJWTService: SessionJWTService) {
    suspend fun auth(authUser: AuthUser): AuthData? {
        userService.findByEmail(authUser.email)?.let {
            if(passwordEncoder.matches(authUser.password, it.password) && it.active == DataState.ACTIVE ) {
                val refreshToken = jwtService.createRefreshJwt(it.id.toString())
                sessionJWTService.createSession(it.id, refreshToken).awaitFirst()
                return AuthData(accessToken = jwtService.createAcсessJwt(it.email), refreshToken = refreshToken, expiresIn = 108000)
            }
        }
        return null
    }
    suspend fun refresh(refreshToken: String): AuthData?{
        sessionJWTService.refreshSession(refreshToken)?.awaitFirst()?.let {
            userService.findById(it.userId.toString()).awaitFirstOrNull()?.let {user->
                return AuthData(accessToken = jwtService.createAcсessJwt(user.email), refreshToken = it.refreshToken, expiresIn = 108000)
            }
        }
        return null
    }
}