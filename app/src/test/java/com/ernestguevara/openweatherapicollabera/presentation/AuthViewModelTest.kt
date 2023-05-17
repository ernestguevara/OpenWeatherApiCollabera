package com.ernestguevara.openweatherapicollabera.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ernestguevara.openweatherapicollabera.MainCoroutineRule
import com.ernestguevara.openweatherapicollabera.domain.repository.AuthRepository
import com.ernestguevara.openweatherapicollabera.getOrAwaitValue
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthViewModel
import com.ernestguevara.openweatherapicollabera.util.Constants
import com.ernestguevara.openweatherapicollabera.util.Resource
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mockViewModel: AuthViewModel

    @Mock
    private lateinit var mockAuthRepository: AuthRepository

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        mockViewModel = AuthViewModel(mockAuthRepository)
    }

    @Test
    fun `should login user`() = mainCoroutineRule.runBlockingTest {
        val response = Resource.Success(firebaseUser)
        whenever(mockAuthRepository.login("test", "test")).thenReturn(
            Resource.Success(
                firebaseUser
            )
        )

        mockViewModel.loginUser("test", "test")

        val result = mockViewModel.loginData.getOrAwaitValue()

        assertThat(result).isEqualTo(response.data)
    }

    @Test
    fun `should not login user due to empty credentials`() = mainCoroutineRule.runBlockingTest {
        whenever(mockAuthRepository.login("", "")).thenReturn(Resource.Error(""))

        mockViewModel.loginUser("", "")

        val result = mockViewModel.errorData.getOrAwaitValue()

        assertThat(result).isEqualTo(Constants.ERROR_INPUTS)
    }

    @Test
    fun `should signup user`() = mainCoroutineRule.runBlockingTest {
        val response = Resource.Success(firebaseUser)
        whenever(mockAuthRepository.signup("test", "test")).thenReturn(
            Resource.Success(
                firebaseUser
            )
        )

        mockViewModel.signupUser("test", "test")

        val result = mockViewModel.signupData.getOrAwaitValue()

        assertThat(result).isEqualTo(response.data)
    }

    @Test
    fun `should not signup user due to empty credentials`() = mainCoroutineRule.runBlockingTest {
        whenever(mockAuthRepository.signup("", "")).thenReturn(Resource.Error(""))

        mockViewModel.signupUser("", "")

        val result = mockViewModel.errorData.getOrAwaitValue()

        assertThat(result).isEqualTo(Constants.ERROR_INPUTS)
    }
}