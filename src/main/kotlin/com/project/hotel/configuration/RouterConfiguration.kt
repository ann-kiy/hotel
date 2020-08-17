package com.project.hotel.configuration

import com.project.hotel.handler.UserHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {
    @FlowPreview
    @Bean
    fun productRoutes(productsHandler: UserHandler) = coRouter {
        POST("/", productsHandler::addUser)
       // POST("/file/{id}", productsHandler::addPhoto)
    }
}