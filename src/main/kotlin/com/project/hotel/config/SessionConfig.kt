package com.project.hotel.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.session.data.mongo.JdkMongoSessionConverter
import org.springframework.session.data.mongo.config.annotation.web.reactive.EnableMongoWebSession
import java.time.Duration


@Configuration
@EnableMongoWebSession
class SessionConfig {
//    @Bean
//    fun jdkMongoSessionConverter(): JdkMongoSessionConverter? {
//        return JdkMongoSessionConverter(Duration.ofMinutes(30))
//    }

}