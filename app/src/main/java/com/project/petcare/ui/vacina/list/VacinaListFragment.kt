package com.project.petcare.ui.vacina.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.project.petcare.databinding.FragmentVacinaListBinding
import com.project.petcare.viewmodel.VacinaViewModel

class VacinaListFragment : Fragment() {

    private var _binding: FragmentVacinaListBinding? = null
    private val binding get() = _binding!!
    private val adapter by lazy { VacinaListAdapter() }
    private lateinit var vacinaViewModel: VacinaViewModel
    private val args by navArgs<VacinaListFragmentArgs>()
    lateinit var mAdview : AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacinaListBinding.inflate(layoutInflater, container, false)
        vacinaViewModel = ViewModelProvider(this).get(VacinaViewModel::class.java)

        binding.rvListaVacinas.adapter = adapter

        MobileAds.initialize(requireContext()) {}
        mAdview = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdview.loadAd(adRequest)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val petId = args.petId

        fabButton(petId)
        updateList(petId)
        updateVacina()
        detailVacina()
        deleteVacina()

    }

    private fun detailVacina() {
        adapter.listenerDetail = { vacina ->
            val action =
                VacinaListFragmentDirections.actionVacinaListFragmentToVacinaDetailFragment(vacina)
            findNavController().navigate(action)
        }
    }

    private fun deleteVacina() {
        adapter.listenerDelete = { vacina ->
            vacinaViewModel.deleteVacina(vacina)

            Snackbar.make(requireView(), "Vacina deletada!", Snackbar.LENGTH_LONG)
                .setAction("Desfazer") {
                    vacinaViewModel.createVacina(vacina)
                }.show()
        }
    }

    private fun updateVacina() {
        adapter.listenerUpdate = { vacina ->
            val action =
                VacinaListFragmentDirections.actionVacinaListFragmentToVacinaUpdateFragment(vacina)
            findNavController().navigate(action)
        }
    }

    private fun updateList(petId: Int) {
        vacinaViewModel.getAllVacinas.observe(viewLifecycleOwner) { lista ->
            val listaFiltrada = lista.filter { it.petId == petId }
            adapter.submitList(listaFiltrada.toMutableList())
        }
    }

    private fun fabButton(petId: Int) {
        binding.floatingActionButton.setOnClickListener {
            val action =
                VacinaListFragmentDirections.actionVacinaListFragmentToVacinaCreateFragment(petId)
            findNavController().navigate(action)
        }
    }
}