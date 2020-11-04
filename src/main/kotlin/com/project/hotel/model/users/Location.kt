package com.project.hotel.model.users

data class Location(
        val lat: Long,
        val lng: Long,
        val country: String,
        val city: String,
        val district: String? = null,
        val street: String,
        val house: String? = null,
        val metro: String? = null
){
    fun equals(other: Any?): Boolean{
        if (other != null) {
            return this.lat == (other as Location).lat && this.lng == other.lng
        }
        return false
    }
}