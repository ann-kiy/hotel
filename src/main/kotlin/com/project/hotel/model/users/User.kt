package com.project.hotel.model.users

import com.project.hotel.model.animals.Animal
import com.project.hotel.util.DataState
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
data class User(
        val name: String,
        @Indexed(unique = true)
        val email: String,
        val phone: String,
        val password: String,
        val roles: Set<Role> = emptySet(),
        val provider: Provider = Provider.LOCAL,
        val active: DataState = DataState.NOT_ACTIVE,
        val activateCode: String? = null,
        val avatar: String? = null,
        val location: Location,
        val animals: List<Animal> = emptyList(),
        @Id
        val id: ObjectId = ObjectId.get()
) {
    var rating: Float = 0.0f
    var countComment:Int = 0
    var lastVisit: Instant = Instant.now()
}
enum class Provider{
    LOCAL, GOOGLE, FACEBOOK, VK
}

