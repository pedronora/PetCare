package com.project.petcare.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.project.petcare.MainActivity
import com.project.petcare.databinding.ActivityLoginBinding
import com.project.petcare.ui.pet.PetMainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, PetMainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        loginUser()
        cancelLogin()
    }

    private fun cancelLogin() {
        binding.btnCancelar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        binding.btnEntrar.setOnClickListener {
            val email = binding.tieEmail.text.toString()
            val senha = binding.tieSenha.text.toString()

            if (inputCheck(email, senha)) {
                auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, PetMainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Email e/ou senha inválidos!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Favor preencher todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun inputCheck(email: String, senha: String): Boolean =
        email.isNotEmpty() && senha.isNotEmpty()
}