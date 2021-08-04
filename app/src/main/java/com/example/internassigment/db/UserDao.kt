package com.example.internassigment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.internassigment.data.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("select * from USERS_INFO")
    suspend fun getUserInfo():List<User>

    @Query("select * from USERS_INFO where email Like:email and password Like:pass")
    suspend fun getSignedInfo(email:String,pass:String):List<User>
}