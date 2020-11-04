package com.project.hotel.repository

import com.project.hotel.model.animals.AnimalBreed
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AnimalBreedRepo: ReactiveMongoRepository<AnimalBreed, String> {
}