package com.example.internassigment.di

import android.app.Application
import androidx.room.Room
import com.example.internassigment.db.MyRoomDatabaseInstance
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MyModule {
    @Singleton
    @Provides
    fun roomInstance(
        app: Application,
        callback: MyRoomDatabaseInstance.MyCallBack
    ): MyRoomDatabaseInstance {
        return Room.databaseBuilder(
            app,
            MyRoomDatabaseInstance::class.java,
            MyRoomDatabaseInstance.My_Db_Name
        ).fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()
    }

    @Provides
    fun getUserDao(roomDatabaseInstance: MyRoomDatabaseInstance) = roomDatabaseInstance.getUserDao()

    @Provides
    fun getCourseDao(roomDatabaseInstance: MyRoomDatabaseInstance) =
        roomDatabaseInstance.getCourseDao()

    @Provides
    fun auth() = FirebaseAuth.getInstance()

    @Provides
    fun getCurrentUser(firebaseAuth: FirebaseAuth) = firebaseAuth.currentUser

    @ApplicationContextDb
    @Singleton
    @Provides
    fun providesCoroutines() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationContextDb