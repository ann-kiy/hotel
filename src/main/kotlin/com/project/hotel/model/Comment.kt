package com.project.hotel.model

import org.bson.types.ObjectId
import java.time.LocalDateTime

data class Comment(
        val id: ObjectId = ObjectId.get(),
        var text:String,
        val idAuth:String,
        var rating:Float,
        var createDate: LocalDateTime
)