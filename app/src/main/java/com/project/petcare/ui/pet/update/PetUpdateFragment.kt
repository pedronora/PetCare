package com.project.petcare.ui.pet.update

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.petcare.R
import com.project.petcare.databinding.FragmentPetUpdateBinding
import com.project.petcare.model.Pet
import com.project.petcare.viewmodel.PetViewModel
import java.io.IOException

class PetUpdateFragment : Fragment() {

    private var _binding: FragmentPetUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var petViewModel: PetViewModel
    private val args by navArgs<PetUpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetUpdateBinding.inflate(layoutInflater, container, false)
        petViewModel = ViewModelProvider(this).get(PetViewModel::class.java)

        fillFields()
        takePicture()
        cancelButton()
        updateButton()

        return binding.root
    }

    private fun cancelButton() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_petUpdateFragment_to_petListFragment)
        }
    }

    private fun fillFields() {
        binding.ivPetPicture.setImageURI(args.currentPet.fileName?.toUri())
        binding.tieNome.setText(args.currentPet.nome)
        binding.tieDate.setText(args.currentPet.dtNasc)
    }

    private fun takePicture() {
        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {

            if (it != null) {
                binding.ivPetPicture.setImageBitmap(it)
            }
        }

        binding.btnTakePicture.setOnClickListener {
            takePhoto.launch()
        }
    }

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap): Boolean {
        return try {
            requireActivity().openFileOutput(filename, Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Couldn't save bitmap.")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private fun updateButton() {
        binding.btnRegistrar.setOnClickListener {
            val id = args.currentPet.id
            val nome = binding.tieNome.text.toString().trim()
            val dtNasc = binding.tieDate.text.toString()
            val filename = args.currentPet.fileName?.takeLast(40)
            val tutorId = args.currentPet.tutorId


            if (inputChecks(nome, dtNasc)) {
                val bitmap = (binding.ivPetPicture.drawable as BitmapDrawable).bitmap
                if (bitmap != null && filename != null) {
                    savePhotoToInternalStorage(filename, bitmap)
                }

                val pathImage = loadUriPhoto(filename).toString()

                val novoPet = Pet(
                    id = id,
                    nome = nome,
                    dtNasc = dtNasc,
                    fileName = pathImage,
                    tutorId = tutorId
                )

                petViewModel.updatePet(novoPet)


                Toast.makeText(
                    requireContext(),
                    "Pet $nome atualizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_petUpdateFragment_to_petListFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, preencha todos os campos!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun loadUriPhoto(fileName: String?): Uri {
        return requireActivity().getFileStreamPath(fileName).toUri()
    }

    private fun inputChecks(nome: String?, dtNsc: String): Boolean =
        nome!!.isNotEmpty() && dtNsc.isNotEmpty()
}
