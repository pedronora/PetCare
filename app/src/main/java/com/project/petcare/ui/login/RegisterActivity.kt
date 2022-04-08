package com.project.petcare.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.MainActivity
import com.project.petcare.databinding.ActivityRegisterBinding
import com.project.petcare.ui.tutor.TutorMainActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        register()
        cancelRegister()
    }

    private fun register() {
        binding.btnCadastrar.setOnClickListener {
            val email = binding.tieEmail.text.toString()
            val senha = binding.tieSenha.text.toString()
            val senhaConfirmacao = binding.tieSenhaConfirmacao.text.toString()

            if (inputCheck(email, senha, senhaConfirmacao)) {
                if (senha.equals(senhaConfirmacao)) {
                    registerUser(email, senha)
                } else {
                    Toast.makeText(
                        this,
                        "A confirmação e a senha devem ser iguais!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this, "Favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, senha: String) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val intent = Intent(this, TutorMainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(
                    baseContext, "Autenticação falhou.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun inputCheck(email: String, senha: String, senhaConfirmacao: String) =
        email.isNotEmpty() && senha.isNotEmpty() && senhaConfirmacao.isNotEmpty()

    private fun cancelRegister() {
        binding.btnCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}