package com.project.hotel.repository

import com.project.hotel.model.animals.Animal
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface AnimalRepo: ReactiveMongoRepository<Animal, String> {
    suspend fun findByUserId(userId:String):Flux<Animal>
}