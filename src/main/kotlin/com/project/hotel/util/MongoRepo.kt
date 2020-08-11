package com.project.hotel.util

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface MongoRepo<T>:MongoRepository<T,String>  {
}