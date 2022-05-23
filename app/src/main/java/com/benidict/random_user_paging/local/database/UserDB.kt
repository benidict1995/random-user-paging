package com.benidict.random_user_paging.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benidict.random_user_paging.local.dao.RemoteKeyDao
import com.benidict.random_user_paging.local.dao.UserDao
import com.benidict.random_user_paging.local.model.RemoteKey
import com.benidict.random_user_paging.local.model.UserLocal

@Database(
    entities = [
        UserLocal::class,
        RemoteKey::class
    ],
    version = 2
)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        @Volatile
        private var instance: UserDB? = null

        fun getDatabase(context: Context): UserDB = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context, UserDB::class.java,
            "user-database"
        ).build()
    }
}