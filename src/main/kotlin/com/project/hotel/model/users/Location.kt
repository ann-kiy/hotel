package com.project.hotel.model.users

data class Location(
        val lat: Long,
        val lng: Long,
        val countryString: String,
        val city: String,
        val district: String? = null,
        val street: String,
        val house: String? = null,
        val metro: String? = null
)