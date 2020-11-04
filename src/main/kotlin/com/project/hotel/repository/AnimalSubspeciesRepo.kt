package com.project.hotel.repository

import com.project.hotel.model.animals.AnimalSubspecies
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface AnimalSubspeciesRepo: ReactiveMongoRepository<AnimalSubspecies, String> {
}