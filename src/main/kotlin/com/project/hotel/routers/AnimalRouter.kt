package com.project.hotel.routers

import com.project.hotel.model.animals.Animal
import com.project.hotel.model.dto.AuthUser
import com.project.hotel.service.AnimalService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.web.reactive.function.server.*

@Configuration
class AnimallRouter(private val animalService: AnimalService) {
    @Bean
    fun animalsRouter()= coRouter {
        "/api/animal".nest {
            POST("/") { request ->
                val animal = request.awaitBodyOrNull<Animal>()
                if (animal != null) {
                    animalService.save(animal)
                    ServerResponse.ok().buildAndAwait()
                } else {
                    ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
                }
            }
            DELETE("/{id}") { request ->
                val animalId:String = request.pathVariable("id")
                if (animalId.isNotEmpty()) {
                    animalService.delete(animalId)
                    ServerResponse.ok().buildAndAwait()
                } else {
                    ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
                }
            }
            PUT("/{id}") { request ->
                val animalId:String = request.pathVariable("id")
                val updateAnimal=request.awaitBodyOrNull<Animal>()
                if (animalId.isNotEmpty() && updateAnimal!=null) {
                    animalService.update(animalId,updateAnimal)
                    ServerResponse.ok().buildAndAwait()
                } else {
                    ServerResponse.status(HttpStatus.BAD_REQUEST).buildAndAwait()
                }
            }
        }
    }
}