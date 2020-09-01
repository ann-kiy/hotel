package com.project.hotel.service.security

import com.project.hotel.model.dto.AuthUser
import com.project.hotel.repository.UserRepo
import com.project.hotel.util.DataState
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(private val userRepo: UserRepo, private val passwordEncoder: PasswordEncoder, private val jwtService: JwtService) {
    suspend fun createJWT(authUser: AuthUser): String? {
        return userRepo.findByEmail(authUser.email)
                .filter { passwordEncoder.matches(authUser.password, it.password) && it.active==DataState.ACTIVE }
                .map { jwtService.createJwt(it.email) }
                .awaitFirstOrNull()
    }
}