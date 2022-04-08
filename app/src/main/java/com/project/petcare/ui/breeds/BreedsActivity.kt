package com.project.petcare.ui.breeds

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.petcare.R
import com.project.petcare.data.RetrofitHelper
import com.project.petcare.data.interfaces.BreedsApi
import com.project.petcare.databinding.ActivityBreedsBinding
import com.project.petcare.ui.pet.PetMainActivity
import com.project.petcare.ui.tutor.TutorMainActivity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBreedsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreedsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataApi()
        bottomNav()

    }

    private fun bottomNav() {
        val btnv = binding.bottomNavigationView
        btnv.selectedItemId = R.id.menu_descubra

        btnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_tutor -> startActivity(Intent(this, TutorMainActivity::class.java))
                R.id.menu_pet -> startActivity(Intent(this, PetMainActivity::class.java))
                R.id.menu_descubra -> startActivity(Intent(this, BreedsActivity::class.java))
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun getDataApi() {
        GlobalScope.launch(Dispatchers.IO) {
            val retrofitClient = RetrofitHelper.getRetrofitInstance()
            val breedsApi = retrofitClient.create(BreedsApi::class.java)

            val response = breedsApi.getBreeds()

            if (response.isSuccessful) {
                response.body()!!.let { lista ->
                    val dog = lista.random()

                    withContext(Dispatchers.Main) {
                        val imageUrl = dog.image.url
                        Picasso.get().load(imageUrl).into(binding.ivDogImage)
                        binding.tvBreed.text = dog.name
                        binding.tvLifespan.text = dog.life_span.replace("years", "anos")
                        binding.tvTemperament.text = dog.temperament
                        (dog.height.metric + " cm").also { binding.tvHeight.text = it }
                        binding.tvOrigin.text = dog.origin
                        binding.tvBredfor.text = dog.bredFor
                    }
                }
            }
        }
    }
}