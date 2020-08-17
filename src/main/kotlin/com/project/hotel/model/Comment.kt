package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "comment")
data class Comment(
        val id: ObjectId = ObjectId.get(),
        var text:String,
        val authId:String,
        val recipientId:String,
        var rating:Float,
        var createDate: LocalDateTime,
        var updateDate: LocalDateTime,
        var state:Boolean?=true
)