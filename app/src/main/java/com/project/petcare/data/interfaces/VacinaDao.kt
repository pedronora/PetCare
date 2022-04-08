package com.project.petcare.data.interfaces

import androidx.lifecycle.LiveData
import androidx.room.*
import com.project.petcare.model.Pet
import com.project.petcare.model.Vacina

@Dao
interface VacinaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createVacina(vacina: Vacina)

    @Update
    suspend fun updateVacina(vacina: Vacina)

    @Delete
    suspend fun deleteVacina(vacina: Vacina)

    @Query("SELECT * FROM Vacina")
    fun getAllVacinas() : LiveData<List<Vacina>>

    @Query("SELECT * FROM Pet WHERE id = :id")
    fun getPetById(id: Int) : LiveData<Pet>
}