package com.project.petcare.repository

import androidx.lifecycle.LiveData
import com.project.petcare.data.interfaces.PetDao
import com.project.petcare.model.Pet

class PetRepository(
    private val petDao: PetDao
) {
    val getAllPets: LiveData<List<Pet>> = petDao.getAllPets()

    suspend fun createPet(pet: Pet) = petDao.createPet(pet)

    suspend fun updatePet(pet: Pet) = petDao.updatePet(pet)

    suspend fun deletePet(pet: Pet) = petDao.deletePet(pet)
}