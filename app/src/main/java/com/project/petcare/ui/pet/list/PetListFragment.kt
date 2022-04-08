package com.project.petcare.ui.pet.list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.R
import com.project.petcare.databinding.FragmentPetListBinding
import com.project.petcare.ui.vacina.VacinaMainActivity
import com.project.petcare.viewmodel.PetViewModel

class PetListFragment : Fragment() {

    private var _binding: FragmentPetListBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { PetListAdapter() }
    private lateinit var petviewmodel: PetViewModel
    private lateinit var auth: FirebaseAuth
    lateinit var mAdview : AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPetListBinding.inflate(layoutInflater, container, false)
        petviewmodel = ViewModelProvider(this).get(PetViewModel::class.java)
        auth = Firebase.auth

        MobileAds.initialize(requireContext()) {}
        mAdview = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdview.loadAd(adRequest)

        binding.rvListPets.adapter = adapter

        updateList()
        createPet()
        updatePet()
        deletePet()
        vaccinesPet()

        return binding.root
    }

    private fun vaccinesPet() {
        adapter.listenerVaccine = {pet ->
            val intent = Intent(requireContext(), VacinaMainActivity::class.java)
            intent.putExtra("petId", pet.id)
            startActivity(intent)
        }
    }

    private fun deletePet() {
        adapter.listenerDelete = { pet ->
            petviewmodel.deletePet(pet)

            Snackbar.make(requireView(), "Pet '${pet.nome}' deletado!", Snackbar.LENGTH_LONG)
                .setAction("Desfazer") {
                    petviewmodel.createPet(pet)
                }.show()
        }
    }

    private fun updatePet() {
        adapter.listenerEdit = { pet ->
            val action = PetListFragmentDirections.actionPetListFragmentToPetUpdateFragment(pet)
            findNavController().navigate(action)
        }
    }

    private fun createPet() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_petListFragment_to_petCreateFragment)
        }
    }

    private fun updateList() {
        petviewmodel.getAllPets.observe(viewLifecycleOwner) { lista ->
               val listaFiltrada = lista.filter { it.tutorId == auth.currentUser!!.uid }
            adapter.submitList(listaFiltrada.toMutableList())
        }
    }
}