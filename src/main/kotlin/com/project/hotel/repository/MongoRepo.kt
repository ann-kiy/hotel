package com.project.hotel.repository

import com.project.hotel.model.users.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface MongoRepo<T>: ReactiveMongoRepository<T, String>  {
}