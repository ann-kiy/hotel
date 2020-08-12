package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Response (
        @Id
        val id: ObjectId = ObjectId.get(),
        val idUser:String,
        val idAdvertisement: String,
        val idAnimal:String,
        var createdDate: LocalDateTime,
        var state:Boolean,
        var stateRecipient:Boolean,
        var stateAuth:Boolean
)