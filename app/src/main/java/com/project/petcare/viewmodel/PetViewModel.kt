package com.project.petcare.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.petcare.data.PetCareDatabase
import com.project.petcare.model.Pet
import com.project.petcare.repository.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PetViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PetRepository
    val getAllPets: LiveData<List<Pet>>

    init {
        val petDao = PetCareDatabase.getDatabase(application).petDao()
        repository = PetRepository(petDao)
        getAllPets = repository.getAllPets
    }

    fun createPet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.createPet(pet)
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updatePet(pet)
        }
    }

    fun deletePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePet(pet)
        }
    }
}