package com.example.internassigment.viewmodle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.db.CourseDao
import com.example.internassigment.db.UserDao
import com.example.internassigment.repso.AuthRepository
import com.example.internassigment.repso.Repository
import com.example.internassigment.utils.SharePreference
import com.example.internassigment.utils.UserStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val courseDao: CourseDao,
    private val userDao: UserDao,
    private val repository: Repository,
    private val authRepository: AuthRepository,
    private val sharePreference: SharePreference
) : ViewModel() {
    var mutableStateFlow = MutableStateFlow<User?>(null)
    var orientationFlag:Boolean?=null
    fun getAllCourse() = repository.getAllCourse(courseDao).asLiveData()
    fun getAllUsers() = repository.getAllUsers(userDao, courseDao).asLiveData()

    fun createUsers(user: User, courseName: CourseName) =
        authRepository.createUserRecord(userDao, user, courseDao, courseName = courseName)
            .asLiveData()

    fun applyForWork(courseName: CourseName) =
        repository.applyForWork(courseDao = courseDao, courseName = courseName).asLiveData()

    fun signInUsers(email: String, password: String, courseName: CourseName) =
        authRepository.signInUser(
            userDao = userDao,
            courseName = courseName,
            courseDao = courseDao,
            email = email,
            password = password
        ).asLiveData()

    val rememberMe = sharePreference.read.asLiveData()

    fun alwaysRememberMe(userStore: UserStore) {
        viewModelScope.launch {
            sharePreference.updateInfo(userStore.email, userStore.password, userStore.flag)
        }
    }
}