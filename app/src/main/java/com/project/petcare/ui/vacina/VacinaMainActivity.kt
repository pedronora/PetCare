package com.project.petcare.ui.vacina

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import com.project.petcare.R
import com.project.petcare.databinding.ActivityVacinaMainBinding
import com.project.petcare.ui.breeds.BreedsActivity
import com.project.petcare.ui.pet.PetMainActivity
import com.project.petcare.ui.tutor.TutorMainActivity
import com.project.petcare.viewmodel.VacinaViewModel

class VacinaMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVacinaMainBinding
    private lateinit var vacinaViewModel: VacinaViewModel
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(binding.fcvVacinas.id) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVacinaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        vacinaViewModel = ViewModelProvider(this).get(VacinaViewModel::class.java)

        val extras = intent.extras
        val petId = extras!!.getInt("petId")
        vacinaViewModel.getPetById(petId).observe(this) { pet ->
            "Pet: ${pet.nome}".also { binding.tvPetName.text = it }
        }

        navController.setGraph(R.navigation.nav_cadastro_vacina, extras)

        bottomNav()

    }

    fun bottomNav() {
        val btnv = binding.bottomNavigationView
        btnv.selectedItemId = R.id.invisible

        btnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_pet -> startActivity(Intent(this, PetMainActivity::class.java))
                R.id.menu_tutor -> startActivity(Intent(this, TutorMainActivity::class.java))
                R.id.menu_descubra -> startActivity(Intent(this, BreedsActivity::class.java))
            }
            return@setOnItemSelectedListener true
        }
    }
}