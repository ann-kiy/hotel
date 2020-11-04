package com.project.hotel.repository

import com.project.hotel.model.advertisements.Advertisement
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface AdvertisementRepo: ReactiveMongoRepository<Advertisement, String> {
    suspend fun findByUserId(userId: String): Flux<Advertisement>?
    suspend fun findByUserIdAndState(userId: String, state: Boolean): Flux<Advertisement>?
}