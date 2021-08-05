package com.example.internassigment.repso

import com.example.internassigment.data.AllData
import com.example.internassigment.data.CourseName
import com.example.internassigment.data.User
import com.example.internassigment.db.CourseDao
import com.example.internassigment.db.UserDao
import com.example.internassigment.utils.MySealed
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor() {

    fun getAllCourse(courseDao: CourseDao) = flow {
        emit(MySealed.Loading("Course is Loading..."))
        kotlinx.coroutines.delay(2000)
        val data = try {
            val courseName = courseDao.getCourseInfo()
            val listOf = mutableListOf<AllData>()
            listOf.add(AllData.Users(User(0, "Friend", "", "", "", "${courseName.isEmpty()}")))
            courseName.forEach { course ->
                listOf.add(AllData.Course(course))
            }
            MySealed.Success(listOf)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)


    fun getAllUsers(email: String, password: String, userDao: UserDao, courseDao: CourseDao) =
        flow {
            emit(MySealed.Loading("User Info is Loading..."))
            kotlinx.coroutines.delay(2000)
            val data = try {
                val userInfo = userDao.getSignedInfo(email, pass = password)
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
        kotlinx.coroutines.delay(2000)
        val data = try {
            courseDao.updateCourseInfo(courseName)
            MySealed.Success(null)
        } catch (e: Exception) {
            MySealed.Error(null, e)
        }
        emit(data)
    }.flowOn(IO)

}