package com.project.petcare.repository

import androidx.lifecycle.LiveData
import com.project.petcare.data.interfaces.VacinaDao
import com.project.petcare.model.Vacina

class VacinaRepository(
    private val vacinaDao: VacinaDao
) {
    val getAllVacinas: LiveData<List<Vacina>> = vacinaDao.getAllVacinas()

    fun getPetById(id: Int)= vacinaDao.getPetById(id)

    suspend fun createVacina(vacina: Vacina) = vacinaDao.createVacina(vacina)

    suspend fun updateVacina(vacina: Vacina) = vacinaDao.updateVacina(vacina)

    suspend fun deleteVacina(vacina: Vacina) = vacinaDao.deleteVacina(vacina)
}