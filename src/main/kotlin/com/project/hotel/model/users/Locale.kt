package com.project.hotel.model.users

data class Locale(
       var lat:Long,
       var lng:Long,
       var countryString:String?=null,
       var city: String?=null,
       var district:String?=null,
       var street:String?=null,
       var house:String?=null,
       var metro:String?=null
)