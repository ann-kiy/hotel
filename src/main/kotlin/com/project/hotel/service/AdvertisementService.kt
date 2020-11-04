package com.project.hotel.service

import com.project.hotel.model.advertisements.Advertisement
import com.project.hotel.model.users.Location
import com.project.hotel.repository.AdvertisementRepo
import com.project.hotel.repository.AnimalBreedRepo
import com.project.hotel.repository.AnimalSubspeciesRepo
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.stereotype.Service

@Service
class AdvertisementService(val advertisementRepo: AdvertisementRepo,
                           val userService: UserService,
                           val animalBreedRepo: AnimalBreedRepo,
                           val animalSubspeciesRepo: AnimalSubspeciesRepo) {
    suspend fun add(advertisement: Advertisement): Advertisement? {
        if (isValidate(advertisement))
            return advertisementRepo.save(advertisement.copy(userLocation = userService.getLocation(advertisement.userId))).awaitFirstOrNull()
        return null
    }

    suspend fun delete(advertisement: Advertisement): Advertisement? {
        if (userService.contains(advertisement.id.toString()))
            advertisementRepo.findById(advertisement.id.toString()).awaitFirstOrNull()?.let{
                it.state = false
                return advertisementRepo.save(it.copy()).awaitFirstOrNull()
            }
        return null
    }

    suspend fun update(idAdvertisement: String, updateAdvertisement: Advertisement): Advertisement? {
        if (isValidate(updateAdvertisement))
            advertisementRepo.findById(idAdvertisement).awaitFirstOrNull()?.let {
                return advertisementRepo.save(it.copy(
                        breedId = updateAdvertisement.breedId,
                        subspeciesId = updateAdvertisement.subspeciesId,
                        dateStart = updateAdvertisement.dateStart,
                        dateEnd = updateAdvertisement.dateEnd,
                        age = updateAdvertisement.age,
                        condition = updateAdvertisement.condition,
                        info = updateAdvertisement.info,
                        sex = updateAdvertisement.sex
                )).awaitFirstOrNull()
            }
        return null
    }

    suspend fun isValidate(advertisement: Advertisement): Boolean {
        var isValid= userService.contains(advertisement.userId) && advertisement.dateEnd.isBefore(advertisement.dateStart)
        advertisement.breedId?.let {
            isValid = isValid && animalBreedRepo.existsById(it.toString()).awaitFirst()
        }
        advertisement.subspeciesId?.let{
            isValid = isValid && animalSubspeciesRepo.existsById(it.toString()).awaitFirst()
        }
        return isValid
    }

    suspend fun updateLocation(userId: String, location: Location){
        getByUser(userId)?.collectList()?.awaitFirstOrNull()?.let {
            it.forEach { advertisement->
                advertisementRepo.save(advertisement.copy(userLocation = location)).awaitFirstOrNull()
            }
        }
    }
    suspend fun getByUser(userId: String)= advertisementRepo.findByUserIdAndState(userId, true)

}