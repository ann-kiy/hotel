package com.project.hotel.repository

import com.project.hotel.model.animals.Animal
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AnimalRepo: ReactiveMongoRepository<Animal, String> {
}