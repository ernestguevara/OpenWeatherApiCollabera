package com.ernestguevara.openweatherapicollabera.domain.repository

import com.ernestguevara.openweatherapicollabera.util.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email: String, password: String): Resource<FirebaseUser?>
    suspend fun signup(email: String, password: String): Resource<FirebaseUser?>
    fun logout()
}