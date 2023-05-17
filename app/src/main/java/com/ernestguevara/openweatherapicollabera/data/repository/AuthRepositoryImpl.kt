package com.ernestguevara.openweatherapicollabera.data.repository

import com.ernestguevara.openweatherapicollabera.domain.repository.AuthRepository
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_LOGIN
import com.ernestguevara.openweatherapicollabera.util.Constants.ERROR_SIGNUP
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val user = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (user.user != null) {
                Resource.Success(user.user)
            } else {
                Resource.Error(data = null, message = ERROR_LOGIN)
            }
        } catch (e: Exception) {
            Resource.Error(data = null, message = e.message)
        }
    }

    override suspend fun signup(email: String, password: String): Resource<FirebaseUser?> {
        return try {
            val user = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (user.user != null) {
                Resource.Success(user.user)
            } else {
                Resource.Error(data = null, message = ERROR_SIGNUP)
            }
        } catch (e: Exception) {
            Resource.Error(data = null, message = e.message)
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}