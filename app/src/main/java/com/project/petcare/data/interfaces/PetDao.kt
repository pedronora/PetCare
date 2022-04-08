package com.project.petcare.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.petcare.model.Pet

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPet(pet: Pet)

    @Update
    suspend fun updatePet(pet: Pet)

    @Delete
    suspend fun deletePet(pet: Pet)

    @Query("SELECT * FROM Pet ORDER BY id ASC")
    fun getAllPets(): LiveData<List<Pet>>
}