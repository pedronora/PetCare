package com.project.petcare.ui.vacina.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.petcare.databinding.FragmentVacinaDetailBinding

class VacinaDetailFragment : Fragment() {

    private var _binding: FragmentVacinaDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<VacinaDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacinaDetailBinding.inflate(layoutInflater, container, false)

        fillFields()
        voltarButon()

        return binding.root
    }

    private fun voltarButon() {
        binding.btnVoltar.setOnClickListener {
            val petId = args.currentVacina.petId
            val action = VacinaDetailFragmentDirections
                .actionVacinaDetailFragmentToVacinaListFragment(petId)
            findNavController().navigate(action)
        }
    }

    private fun fillFields() {
        binding.tvNomeVacina.text = args.currentVacina.nomeVacina
        binding.tvDataAplicacao.text = args.currentVacina.dataAplicacao
        binding.tvProximaAplicacao.text = args.currentVacina.proximaAplicacao
    }
}