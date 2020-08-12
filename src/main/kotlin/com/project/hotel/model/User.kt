package com.project.hotel.model

import com.project.hotel.util.Locale
import com.project.hotel.util.Role
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection="usr")
data class User(
        @Id
        val id: ObjectId =ObjectId.get(),
        val idWeb:String?=null,
        var name: String?=null,
        var img: String?=null,
        var email: String?=null,
        var phone: String?=null,
        var password: String?=null,
        var roles: Set<Role>?=null,
        var active:Boolean?=false,
        var activateCode: String?=null,
        var lastVisit:LocalDateTime?=null,
        var locale:Locale?=null,
        var comments:List<Comment>?=null
)

