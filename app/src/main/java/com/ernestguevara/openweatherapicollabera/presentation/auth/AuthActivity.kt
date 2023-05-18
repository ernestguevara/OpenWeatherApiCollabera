package com.ernestguevara.openweatherapicollabera.presentation.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.ernestguevara.openweatherapicollabera.BaseActivity
import com.ernestguevara.openweatherapicollabera.databinding.ActivityAuthBinding
import com.ernestguevara.openweatherapicollabera.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityAuthBinding

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        if (authViewModel.currentUser != null) {
            openMainActivity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun openMainActivity() {
        startActivity(Intent(this@AuthActivity, MainActivity::class.java))
        finish()
    }
}