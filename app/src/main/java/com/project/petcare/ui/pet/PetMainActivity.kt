package com.project.petcare.ui.pet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.project.petcare.R
import com.project.petcare.databinding.ActivityPetMainBinding
import com.project.petcare.ui.breeds.BreedsActivity
import com.project.petcare.ui.tutor.TutorMainActivity

class PetMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPetMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetMainBinding.inflate(layoutInflater)

        bottomNav()

        setContentView(binding.root)
    }

    fun bottomNav() {
        val btnv = binding.bottomNavigationView
        btnv.selectedItemId = R.id.menu_pet

        btnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_tutor -> startActivity(Intent(this, TutorMainActivity::class.java))
                R.id.menu_descubra -> startActivity(Intent(this, BreedsActivity::class.java))
            }
            return@setOnItemSelectedListener true
        }
    }
}