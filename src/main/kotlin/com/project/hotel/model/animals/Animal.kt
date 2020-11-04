package com.project.hotel.model.animals

import com.fasterxml.jackson.annotation.JsonFormat
import com.project.hotel.model.Comment
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
import java.util.*

@Document
data class Animal(
        val userId: String,
        val name: String,
        val sex: Sex,
        val subspeciesId: String,
        val breedId: String,
        @JsonFormat(pattern="dd.MM.yyyy")
        val dateOfBirth: Date,
        val avatar: String,
        val info: String,
        @Id
        val id: ObjectId = ObjectId.get()
){
        var createDate: Instant = Instant.now()
        var state: Boolean = true
        var rating: Float = 0.0f
        var countComment:Int = 0
}

enum class Sex { M, F }

