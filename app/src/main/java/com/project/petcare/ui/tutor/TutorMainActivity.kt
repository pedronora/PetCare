package com.project.petcare.ui.tutor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.R
import com.project.petcare.databinding.ActivityTutorMainBinding
import com.project.petcare.ui.breeds.BreedsActivity
import com.project.petcare.ui.pet.PetMainActivity

class TutorMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorMainBinding.inflate(layoutInflater)

        bottomNav()
        setContentView(binding.root)
    }

    fun bottomNav() {
        val btnv = binding.bottomNavigationView
        btnv.selectedItemId = R.id.menu_tutor

        btnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_pet -> startActivity(Intent(this, PetMainActivity::class.java))
                R.id.menu_descubra -> startActivity(Intent(this, BreedsActivity::class.java))
            }
            return@setOnItemSelectedListener true
        }
    }
}