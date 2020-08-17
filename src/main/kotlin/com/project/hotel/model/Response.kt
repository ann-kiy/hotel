package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "response")
data class Response (
        @Id
        val id: ObjectId = ObjectId.get(),
        val userId:String,
        val advertisementId: String,
        val animalId:String,
        var createdDate: LocalDateTime,
        var updateDate: LocalDateTime,
        var state:Set<ResponseState>
)
enum class ResponseState {
        active, noActive, confirmed, noConfirmed, canceled
}