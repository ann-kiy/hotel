package com.project.hotel.model.advertisements

import com.project.hotel.model.animals.AnimalBreed
import com.project.hotel.model.animals.AnimalSubspecies
import com.project.hotel.model.animals.Sex
import com.project.hotel.model.users.Locale
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.Instant

@Document(collection = "advertisement")
data class Advertisement(
        @Id
        val id: ObjectId = ObjectId.get(),
        val userId: String,
        @Field("usr.locale")
        val userLocale: Locale,
        val animalSubspecies: AnimalSubspecies? = null,
        val breedType: AnimalBreed? = null,
        val dateStart: Instant,
        val dateEnd: Instant,
        val condition: Condition,
        val state: Boolean? = true,
        val info: String? = null,
        val age: Byte? = null,
        val sex: Sex? = null
){
        var createDate: Instant= Instant.now()
}

enum class Condition { FREE_OF_CHARGE, FOR_REWARD, FOR_MONEY }