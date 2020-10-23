package com.project.hotel.service

import com.project.hotel.model.animals.Animal
import com.project.hotel.repository.AnimalRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AnimalService(val animalRepo: AnimalRepo) {
    suspend fun save(animal: Animal){
        animalRepo.save(animal).awaitFirst()
    }
    suspend fun findById(animalId: String):Animal?=animalRepo.findById(animalId).awaitFirstOrNull()

    suspend fun delete(animalId:String){
        animalRepo.findById(animalId).awaitFirstOrNull()?.let {
            it.state = false
            animalRepo.save(it).awaitFirst()
        }

    }
    suspend fun update(animalId: String, animal: Animal){
        animalRepo.findById(animalId).awaitFirstOrNull()?.let {
            animalRepo.save(it.copy(
                    sex=animal.sex,
                    name = animal.name,
                    breedId = animal.breedId,
                    typeId = animal.typeId,
                    dateOfBirth = animal.dateOfBirth,
                    info = animal.info)).awaitFirst()
        }
    }
    suspend fun updateRating(animalId: String, rating: Float):Float{
        animalRepo.findById(animalId).awaitFirstOrNull()?.let{
            val amount = (it.rating * it.countComment) + rating
            it.rating = amount / (it.countComment + 1)
            it.countComment++
            animalRepo.save(it)
            return it.rating
        }
        return Float.NaN
    }
    suspend fun cancelRating(animalId: String, rating: Float):Float{
        animalRepo.findById(animalId).awaitFirstOrNull()?.let{
            if(it.rating>=rating && it.countComment>0) {
                it.rating -= rating
                it.countComment--
                animalRepo.save(it)
                return it.rating
            }
        }
        return Float.NaN
    }
    suspend fun getByUser(userId:String)=animalRepo.findByUserId(userId).awaitFirstOrNull()
}