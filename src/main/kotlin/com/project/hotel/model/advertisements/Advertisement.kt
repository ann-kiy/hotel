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
        var createDate: Instant,
        var animalSubspecies: AnimalSubspecies? = null,
        var breedType: AnimalBreed? = null,
        var dateStart: Instant,
        var dateEnd: Instant,
        var condition: Condition,
        var state: Boolean? = true,
        var info: String? = null,
        var age: Byte? = null,
        var sex: Sex? = null
)

enum class Condition { FREE_OF_CHARGE, FOR_REWARD, FOR_MONEY }