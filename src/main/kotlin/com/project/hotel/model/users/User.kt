package com.project.hotel.model.users

import com.project.hotel.model.animals.Animal
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime

@Document(collection = "usr")
data class User(
        @Id
        val id: ObjectId = ObjectId.get(),
        val webId: String? = null,
        val name: String,
        val email: String,
        val phone: String,
        val password: String,
        val roles: Set<Role> = emptySet(),
        val active: Boolean = false,
        val activateCode: String? = null,
        val img: String? = null,
        val rating: Float? = 0.0f,
        val locale: Locale,
        val animals: List<Animal> = emptyList()
) {
    var lastVisit: Instant = Instant.now()

}

