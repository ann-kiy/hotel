package com.project.hotel.repo

import com.project.hotel.model.User
import com.project.hotel.util.MongoRepo

interface UserRepo:MongoRepo<User> {
}