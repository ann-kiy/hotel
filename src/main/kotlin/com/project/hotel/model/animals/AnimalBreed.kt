package com.project.hotel.model.animals

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "breedAnimal")
data class AnimalBreed(
        @Id
        val id: ObjectId = ObjectId.get(),
        val subspeciesId: String,
        val breed: String
)