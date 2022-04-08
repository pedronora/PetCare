package com.project.petcare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.petcare.data.PetCareDatabase
import com.project.petcare.model.Vacina
import com.project.petcare.repository.VacinaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VacinaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VacinaRepository
    val getAllVacinas: LiveData<List<Vacina>>

    init {
        val vacinaDao = PetCareDatabase.getDatabase(application).vacinaDao()
        repository = VacinaRepository(vacinaDao)
        getAllVacinas = repository.getAllVacinas
    }

    fun getPetById(id: Int) = repository.getPetById(id)

    fun createVacina(vacina: Vacina) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createVacina(vacina)
        }
    }

    fun updateVacina(vacina: Vacina) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateVacina(vacina)
        }
    }

    fun deleteVacina(vacina: Vacina) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteVacina(vacina)
        }
    }
}