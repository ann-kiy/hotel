package com.project.hotel.model.dto

import com.project.hotel.model.users.Location
import com.project.hotel.model.users.Provider
import com.project.hotel.model.users.User
import org.springframework.data.mongodb.core.mapping.Document

data class NewUser(
        val name: String,
        val email: String,
        val phone: String,
        val password: String,
        val location: Location
//        val provider: Provider=Provider.LOCAL
) {
    fun toUser() = User(name = this.name, email = this.email, phone = this.phone, password = this.password, location = this.location)
}