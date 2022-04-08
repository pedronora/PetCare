package com.project.petcare.ui.tutor.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.MainActivity
import com.project.petcare.R
import com.project.petcare.databinding.FragmentTutorDetailBinding

class DetailTutorFragment : Fragment() {

    private var _binding: FragmentTutorDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorDetailBinding.inflate(layoutInflater, container, false)

        auth = Firebase.auth
        fillFields()
        updateTutor()
        sair()

        return binding.root
    }

    private fun sair() {
        binding.btnSair.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fillFields() {
        binding.tvNomeTutor.text = auth.currentUser!!.email!!
            .split("@")
            .first()
            .replace(".", " ")
            .replace("-", " ")
            .replace("_", " ")
            .uppercase()

        binding.tvEmailTutor.text = auth.currentUser!!.email!!
    }

    private fun updateTutor() {
        binding.btnAtualizarCadastro.setOnClickListener {
            findNavController().navigate(R.id.action_detailTutorFragment_to_updateTutorFragment)
        }
    }
}