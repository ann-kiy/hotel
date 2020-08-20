package com.project.hotel.model.animals

import com.project.hotel.model.Comment
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

data class Animal(
        @Id
        val id: ObjectId = ObjectId.get(),
        val userId: String,
        val name: String,
        val sex: Sex,
        val typeId: String,
        val breedId: String,
        val age: Byte,
        val img: String,
        val info: String,
        val rating: Float? = 0.0f,
        val state: Boolean? = true,
        val comments: List<Comment>? = arrayListOf()
){
        var createDate: Instant = Instant.now()
}

enum class Sex { m, f }

