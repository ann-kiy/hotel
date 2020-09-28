package com.project.hotel.model.dto

class AuthData(val refreshToken: String,
               val accessToken: String,
               val expiresIn: Long) {
}