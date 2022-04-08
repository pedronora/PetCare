package com.project.petcare.ui.tutor.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.R
import com.project.petcare.databinding.FragmentTutorUpdateBinding

class UpdateTutorFragment : Fragment() {

    private var _binding: FragmentTutorUpdateBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTutorUpdateBinding.inflate(layoutInflater, container, false)

        auth = Firebase.auth
        fillFields()
        cancelButton()
        updateButton()

        return binding.root
    }

    private fun updateButton() {
        binding.btnAtualizar.setOnClickListener {
            val nome = binding.tieNome.text.toString()
            val email = binding.tieEmail.text.toString()
            val senhaAtual = binding.tieSenhaAtual.text.toString()
            val senha = binding.tieSenha.text.toString()
            val confirmacao = binding.tieSenhaConfirmacao.text.toString()

            if (inputChecks(nome, email, senha, confirmacao, senhaAtual)) {

                if (senha.equals(confirmacao)) {

                    auth.signInWithEmailAndPassword(email, senhaAtual)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                auth.currentUser!!.updatePassword(senha)

                                findNavController().navigate(R.id.action_updateTutorFragment_to_detailTutorFragment)

                                Toast.makeText(
                                    requireContext(),
                                    "Informações atualizadas com sucesso!",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Email e/ou senha atuais não conferem",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Senha e confirmação não conferem!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    requireContext(),
                    "Favor preencher todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun cancelButton() {
        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_updateTutorFragment_to_detailTutorFragment)
        }
    }

    private fun fillFields() {
        binding.tieNome.setText(
            auth.currentUser!!.email!!
                .split("@")
                .first()
                .replace(".", " ")
                .replace("-", " ")
                .replace("_", " ")
                .replaceFirstChar { first -> first.titlecase() }
        )
        binding.tieEmail.setText(auth.currentUser!!.email!!)
    }

    private fun inputChecks(
        nome: String,
        email: String,
        senha: String,
        confirmacao: String,
        senhaAtual: String
    ) =
        nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty() && confirmacao.isNotEmpty() && senhaAtual.isNotEmpty()
}