package com.project.hotel

import com.project.hotel.model.users.User
import com.project.hotel.repository.UserRepo
import com.project.hotel.service.FileStorageService
import com.project.hotel.service.UserService
import kotlinx.coroutines.reactive.awaitFirst
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.logging.Logger

@SpringBootApplication
@EnableReactiveMongoRepositories
class HotelApplication

fun main(args: Array<String>) {
	runApplication<HotelApplication>(*args)
}

//@RestController
//class MainController(private val userService: UserService){
//	private val logger: org.slf4j.Logger? = LoggerFactory.getLogger(HotelApplication::class.java)
//	@PostMapping
//	suspend fun main(@RequestBody user: User){
//		userService.addUser(user)
////		userService.addFile(user, file)
//	}
//	@PostMapping("/file", consumes = arrayOf("multipart/form-data"))
//	suspend fun main1(@RequestPart("user") user:User, @RequestPart(value="photo", required = false) file:MultipartFile ){
//
////		val user=userService.findById(userId).awaitFirst()
//		userService.addFile(user, file)
//	}
////	@GetMapping
////	fun mainn():String{
////		logger?.info("start")
////		var k:Int =0
////		for(i in 1..1000000){k++}
////		for(i in 1..1000000){k++}
////		logger?.info("end")
////		return "ghjkl"
////	}
//}
