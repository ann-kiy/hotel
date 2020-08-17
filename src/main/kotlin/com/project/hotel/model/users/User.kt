package com.project.hotel.model.users

import com.project.hotel.model.animals.Animal
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection="usr")
data class User(
        @Id
        val id: ObjectId =ObjectId.get(),
        val webId:String?=null,
        var name: String,
        var img: String?=null,
        var email: String,
        var phone: String,
        var password: String,
        var roles: Set<Role>?=null,
        var rating:Float?=0.0f,
        var active:Boolean?=false,
        var activateCode: String? = null,
        var lastVisit:LocalDateTime? = null,
        var locale: Locale,
        var animals:List<Animal>?= arrayListOf()
)

