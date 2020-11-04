package com.project.hotel.model.advertisements

import com.fasterxml.jackson.annotation.JsonFormat
import com.project.hotel.model.animals.AnimalBreed
import com.project.hotel.model.animals.AnimalSubspecies
import com.project.hotel.model.animals.Sex
import com.project.hotel.model.users.Location
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "advertisement")
data class Advertisement(
        val userId: String,
        @Field("usr.locale")
        val userLocation: Location?=null,
        val subspeciesId: String? = null,
        val breedId: String? = null,
        @JsonFormat(pattern="dd.MM.yyyy")
        val dateStart: Instant,
        @JsonFormat(pattern="dd.MM.yyyy")
        val dateEnd: Instant,
        val condition: Condition,
        val info: String? = null,
        val age: Int? = null,
        val sex: Sex? = null,
        @Id
        val id: ObjectId = ObjectId.get()
){
        var createDate: Instant= Instant.now()
        var state: Boolean = true
}

enum class Condition { FREE_OF_CHARGE, FOR_REWARD, FOR_MONEY }