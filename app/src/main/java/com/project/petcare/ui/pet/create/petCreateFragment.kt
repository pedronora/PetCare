package com.project.petcare.ui.pet.create

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
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.R
import com.project.petcare.databinding.FragmentPetCreateBinding
import com.project.petcare.extensions.format
import com.project.petcare.model.Pet
import com.project.petcare.viewmodel.PetViewModel
import java.io.IOException
import java.util.*

class petCreateFragment : Fragment() {

    private var _binding: FragmentPetCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var petviewmodel: PetViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetCreateBinding.inflate(layoutInflater, container, false)
        petviewmodel = ViewModelProvider(this).get(PetViewModel::class.java)
        auth = Firebase.auth


        onClickDate()
        cancelButton()
        takePicture()
        addButton()

        return binding.root
    }

    private fun onClickDate() {
        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            val timeZone = TimeZone.getDefault()
            val offSet = timeZone.getOffset(Date().time) * -1
            datePicker.addOnPositiveButtonClickListener {
                binding.tieDate.setText(Date(it + offSet).format())
            }
            datePicker.show(parentFragmentManager, "DATE_PICKER_TAG")
        }
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
            requireActivity().openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
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

    private fun cancelButton() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_petCreateFragment_to_petListFragment)
        }
    }

    private fun addButton() {
        binding.btnRegistrar.setOnClickListener {
            val nome = binding.tieNome.text.toString().trim()
            val dtNasc = binding.tieDate.text.toString()
            val filename = UUID.randomUUID().toString()
            val tutorId = auth.currentUser!!.uid

            if (inputChecks(nome, dtNasc)) {
                val bitmap = (binding.ivPetPicture.drawable as BitmapDrawable).bitmap
                savePhotoToInternalStorage(filename, bitmap)
                val pathImage = loadUriPhoto(filename).toString()

                val novoPet = Pet(0, nome, dtNasc, pathImage, tutorId)
                petviewmodel.createPet(novoPet)

                Toast.makeText(
                    requireContext(),
                    "Pet $nome cadastrado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                findNavController().navigate(R.id.action_petCreateFragment_to_petListFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Por favor, preencha todos os campos!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun loadUriPhoto(fileName: String): Uri {
        return requireActivity().getFileStreamPath("$fileName.jpg").toUri()
    }

    private fun inputChecks(nome: String, dtNsc: String): Boolean =
        nome.isNotEmpty() && dtNsc.isNotEmpty()
}