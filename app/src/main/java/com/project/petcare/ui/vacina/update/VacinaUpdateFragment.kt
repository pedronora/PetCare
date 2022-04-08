package com.project.petcare.ui.vacina.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.project.petcare.databinding.FragmentVacinaUpdateBinding
import com.project.petcare.extensions.format
import com.project.petcare.model.Vacina
import com.project.petcare.viewmodel.VacinaViewModel
import java.util.*

class VacinaUpdateFragment : Fragment() {

    private var _binding: FragmentVacinaUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var vacinaViewModel: VacinaViewModel
    private val args by navArgs<VacinaUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacinaUpdateBinding.inflate(layoutInflater, container, false)
        vacinaViewModel = ViewModelProvider(this).get(VacinaViewModel::class.java)

        fillFields()
        updateVacina()
        cancelBtn()
        onClickDate()
        onClickDateProx()

        return binding.root
    }

    private fun cancelBtn() {
        val petId = args.currentVacina.petId
        binding.btnCancel.setOnClickListener {
            val action = VacinaUpdateFragmentDirections
                .actionVacinaUpdateFragmentToVacinaListFragment(petId)
            findNavController().navigate(action)
        }
    }

    private fun fillFields() {
        binding.tieTipo.setText(args.currentVacina.nomeVacina)
        binding.tieData.setText(args.currentVacina.dataAplicacao)
        binding.tieDataProx.setText(args.currentVacina.proximaAplicacao)
    }

    private fun updateVacina() {
        binding.btnUpdate.setOnClickListener {
            val id = args.currentVacina.id
            val tipo = binding.tieTipo.text.toString()
            val data = binding.tieData.text.toString()
            val dataProx = binding.tieDataProx.text.toString()
            val petId = args.currentVacina.petId

            if (inputChecks(tipo, data, dataProx)) {
                val vacina = Vacina(id, tipo, data, dataProx, petId)
                vacinaViewModel.updateVacina(vacina)

                val action =
                    VacinaUpdateFragmentDirections
                        .actionVacinaUpdateFragmentToVacinaListFragment(petId)
                findNavController().navigate(action)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Favor preencher todos so campos!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun inputChecks(tipo: String, data: String, dataProx: String): Boolean =
        tipo.isNotEmpty() && data.isNotEmpty() && dataProx.isNotEmpty()

    private fun onClickDate() {
        binding.tilData.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            val timeZone = TimeZone.getDefault()
            val offSet = timeZone.getOffset(Date().time) * -1
            datePicker.addOnPositiveButtonClickListener {
                binding.tieData.setText(Date(it + offSet).format())
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")
        }
    }

    private fun onClickDateProx() {
        binding.tilDataProx.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            val timeZone = TimeZone.getDefault()
            val offSet = timeZone.getOffset(Date().time) * -1
            datePicker.addOnPositiveButtonClickListener {
                binding.tieDataProx.setText(Date(it + offSet).format())
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")
        }
    }
}