package com.project.hotel

import com.project.hotel.model.User
import com.project.hotel.repo.UserRepo
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableMongoRepositories
class HotelApplication

fun main(args: Array<String>) {
	runApplication<HotelApplication>(*args)
}

@RestController
class MainController(val userRepo: UserRepo){
	@PostMapping
	fun main(@RequestBody user: User){
		userRepo.save(user)
	}
}
