package com.project.hotel.model.dto

import com.project.hotel.model.users.Locale
import com.project.hotel.model.users.Role
import com.project.hotel.model.users.User
import java.time.Instant

data class NewUser(
        var name: String,
        var email: String,
        var phone: String,
        var password: String,
        var locale: Locale
) {
    fun toUser() = User(name = this.name, email = this.email, phone = this.phone, password = this.password, locale = this.locale)
}