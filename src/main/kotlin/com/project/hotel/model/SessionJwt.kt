package com.project.hotel.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document
class SessionJwt(val creationTime: Instant = Instant.now(),
                 val userId: ObjectId,
                 @Indexed(unique = true)
                 val refreshToken: String,
                 @Indexed(expireAfter = "0")
                 val expireAdd: Instant = Instant.now().plusSeconds(110000),
                 @Id
                 val id: ObjectId = ObjectId.get())