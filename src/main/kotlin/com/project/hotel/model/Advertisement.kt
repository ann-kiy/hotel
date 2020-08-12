package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Advertisement (
        @Id
        val id: ObjectId = ObjectId.get(),
        var idUser:User,
        var createDate:LocalDateTime,
        var typeAnimal:TypeAnimal?=null,
        var dateStart:LocalDateTime,
        var dateEnd:LocalDateTime,
        var condition:Condition,
        var  state:Boolean?=true,
        var info:String?=null,
        var age:Byte?=null,
        var sex:Sex?=null
)
enum class Condition{Безвозмездно, За_вознаграждение, За_деньги}