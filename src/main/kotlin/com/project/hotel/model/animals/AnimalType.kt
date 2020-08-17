package com.project.hotel.model.animals

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "typeAnimal")
data class AnimalType(
        @Id
        val id: ObjectId = ObjectId.get(),
        var type:String
)