package com.dicoding.kenalikan.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.kenalikan.ViewModelFactory
import com.dicoding.kenalikan.data.preference.UserModel
import com.dicoding.kenalikan.databinding.ActivityLoginBinding
import com.dicoding.kenalikan.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            viewModel.saveSession(UserModel(email, "sample_token"))
                            Toast.makeText(this, "Login Berhasil!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("USER_EMAIL", email) // Pass the email
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Gagal!", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Masukkan Email dan Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}