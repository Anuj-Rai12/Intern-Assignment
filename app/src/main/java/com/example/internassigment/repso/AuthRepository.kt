package com.example.internassigment.repso

import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.db.CourseDao
import com.example.internassigment.db.UserDao
import com.example.internassigment.utils.MySealed
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun createUserRecord(
        userDao: UserDao,
        user: User,
        courseDao: CourseDao,
        courseName: CourseName
    ) = flow {
        emit(MySealed.Loading("User Profile is Creating..."))
        val data = try {
            firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).await()
            userDao.insertUser(user)
            courseDao.updateCourseInfo(courseName)
            MySealed.Success(null)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)

    fun signInUser(
        courseDao: CourseDao,
        email: String,
        password: String,
        courseName: CourseName
    ) = flow {
        emit(MySealed.Loading("Validating User Details.."))
        val data = try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            courseDao.updateCourseInfo(courseName)
            MySealed.Success(null)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)
}