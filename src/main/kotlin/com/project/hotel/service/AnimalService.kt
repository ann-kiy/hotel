package com.project.hotel.service

import com.project.hotel.model.advertisements.Advertisement
import com.project.hotel.model.animals.Animal
import com.project.hotel.repository.AnimalBreedRepo
import com.project.hotel.repository.AnimalRepo
import com.project.hotel.repository.AnimalSubspeciesRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AnimalService(val animalRepo: AnimalRepo,
                    val userService: UserService,
                    val animalBreedRepo: AnimalBreedRepo,
                    val animalSubspeciesRepo: AnimalSubspeciesRepo) {
    suspend fun save(animal: Animal):Animal? {
        if(isValidate(animal))
            return animalRepo.save(animal).awaitFirstOrNull()
        return null
    }

    suspend fun findById(animalId: String): Animal? = animalRepo.findById(animalId).awaitFirstOrNull()

    suspend fun delete(animalId: String):Animal? =
        animalRepo.findById(animalId).awaitFirstOrNull()?.let {
            it.state = false
            animalRepo.save(it).awaitFirst()
        }

    suspend fun update(animalId: String, animal: Animal): Animal? {
        if(isValidate(animal))
            animalRepo.findById(animalId).awaitFirstOrNull()?.let {
                return animalRepo.save(it.copy(
                        sex = animal.sex,
                        name = animal.name,
                        breedId = animal.breedId,
                        subspeciesId = animal.subspeciesId,
                        dateOfBirth = animal.dateOfBirth,
                        info = animal.info)).awaitFirst()
            }
        return null
    }

    suspend fun updateRating(animalId: String, rating: Float): Float {
        animalRepo.findById(animalId).awaitFirstOrNull()?.let {
            val amount = (it.rating * it.countComment) + rating
            it.rating = amount / ++it.countComment
            animalRepo.save(it).awaitFirstOrNull()
            return it.rating
        }
        return Float.NaN
    }

    suspend fun cancelRating(animalId: String, rating: Float): Float {
        animalRepo.findById(animalId).awaitFirstOrNull()?.let {
            if (it.countComment > 0) {
                val amount = (it.rating * it.countComment) - rating
                it.rating = amount / --it.countComment
                animalRepo.save(it).awaitFirstOrNull()
                return it.rating
            }
        }
        return Float.NaN
    }

    suspend fun getByUser(userId: String) = animalRepo.findByUserId(userId).awaitFirstOrNull()
    suspend fun isValidate(animal:Animal): Boolean =
            userService.contains(animal.userId) && animalBreedRepo.existsById(animal.breedId.toString()).awaitFirst() && animalSubspeciesRepo.existsById(animal.subspeciesId).awaitFirst()

}