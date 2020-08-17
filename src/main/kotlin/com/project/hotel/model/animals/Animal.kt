package com.project.hotel.model.animals

import com.project.hotel.model.Comment
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Animal (
        @Id
        val id: ObjectId = ObjectId.get(),
        var userId:String,
        var name:String,
        var sex: Sex,
        var typeId:String,
        var breedId:String,
        var age:Byte,
        var img:String,
        var info:String,
        var rating:Float?=null,
        var state:Boolean?=true,
        var comments:List<Comment>?=null
)
enum class Sex{m,f}

