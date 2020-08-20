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
