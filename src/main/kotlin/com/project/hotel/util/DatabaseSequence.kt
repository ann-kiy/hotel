package com.project.hotel.util

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "database_sequences")
class DatabaseSequence {
    @Id
    val id: String? = null
    val seq: Long = 0
}