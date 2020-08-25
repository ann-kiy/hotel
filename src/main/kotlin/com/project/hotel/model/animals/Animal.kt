package com.project.hotel.model.animals

import com.project.hotel.model.Comment
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

data class Animal(
        val userId: String,
        val name: String,
        val sex: Sex,
        val typeId: String,
        val breedId: String,
        val age: Byte,
        val avatar: String,
        val info: String,
        val state: Boolean = true,
        val comments: List<Comment> = emptyList<Comment>(),
        @Id
        val id: ObjectId = ObjectId.get()
){
        var createDate: Instant = Instant.now()
        val rating: Float = 0.0f
}

enum class Sex { M, F }

