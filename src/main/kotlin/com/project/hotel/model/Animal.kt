package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Animal (
        @Id
        val id: ObjectId = ObjectId.get(),
        var idUser:String,
        var name:String,
        var sex:Sex,
        var type:TypeAnimal,
        var age:Byte,
        var img:String,
        var info:String,
        var rating:Float?=null,
        var state:Boolean?=true
)
enum class Sex{m,f}
@Document
data class TypeAnimal(
       var type:String,
       var breed:String?=null
)