package com.example.internassigment.repso

import android.util.Log
import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.db.CourseDao
import com.example.internassigment.db.UserDao
import com.example.internassigment.utils.MySealed
import com.example.internassigment.utils.TAG
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor() {

    fun getAllCourse(courseDao: CourseDao) = flow {
        emit(MySealed.Loading("Course is Loading..."))
        kotlinx.coroutines.delay(2000)
        val data = try {
            val courseName = courseDao.getCourseInfo()
            val listOf = mutableListOf<AllData>()
            listOf.add(AllData.Users(User(0, "Friend", "", "", "", "")))
            courseName.forEach { course ->
                listOf.add(AllData.Course(course))
            }
            Log.i(TAG, "getAllCourse: $listOf")
            MySealed.Success(listOf)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)


    fun getAllUsers(userDao: UserDao, courseDao: CourseDao) = flow {
        emit(MySealed.Loading("User Info is Loading..."))
        kotlinx.coroutines.delay(2000)
        val data = try {
            val userInfo = userDao.getUserInfo()
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

    fun applyForWork(courseDao: CourseDao, courseName: CourseName) = flow {
        emit(MySealed.Loading("Applying For Work Shop"))
        val data = try {
            courseDao.updateCourseInfo(courseName)
            MySealed.Success(null)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)

}