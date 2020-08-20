package com.project.hotel.model.users

data class Locale(
        var lat: Long,
        var lng: Long,
        var countryString: String,
        var city: String?,
        var district: String? = null,
        var street: String,
        var house: String? = null,
        var metro: String? = null
)