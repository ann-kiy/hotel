package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime

@Document(collection = "response")
data class Response(
        @Id
        val id: ObjectId = ObjectId.get(),
        val userId: String,
        val advertisementId: String,
        val animalId: String,
        val createdDate: Instant = Instant.now(),
        var updateDate: Instant,
        var state: Set<ResponseState>
)

enum class ResponseState {
    ACTIVE,
    NOT_ACTIVE,
    CONFIRMED,
    NOT_CONFIRMED,
    CANCELED
}