package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.time.LocalDateTime

@Document
data class Comment(
        val text: String,
        val authId: String,
        val recipientId: String,
        val rating: Float,
        val createDate: Instant = Instant.now(),
        @Id
        val id: ObjectId = ObjectId.get()
) {
    var updateDate: Instant = Instant.now()
    var state: Boolean = true
}