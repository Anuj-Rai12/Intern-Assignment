package com.example.internassigment.repso

import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.db.CourseDao
import com.example.internassigment.db.UserDao
import com.example.internassigment.utils.MySealed
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val user: FirebaseUser?
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
        userDao: UserDao,
        courseDao: CourseDao,
        email: String,
        password: String,
        courseName: CourseName
    ) = flow {
        emit(MySealed.Loading("Validating User Details.."))
        val data = try {
            kotlinx.coroutines.delay(20000)
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val userInfo = userDao.getSignedInfo(email, pass = password)
            courseDao.updateCourseInfo(courseName)
            val selected = courseDao.getCourseSelectedByUsers()
            val listOf = mutableListOf<AllData>()
            userInfo.forEach { user ->
                listOf.add(AllData.Users(user))
            }
            selected.forEach { course ->
                listOf.add(AllData.Course(course))
            }
            MySealed.Success(listOf)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)
}