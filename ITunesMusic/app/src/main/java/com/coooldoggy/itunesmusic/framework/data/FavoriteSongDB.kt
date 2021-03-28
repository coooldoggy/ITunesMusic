package com.coooldoggy.itunesmusic.framework.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavoriteSongEntity::class], version = 1)
abstract class FavoriteSongDB: RoomDatabase() {

    abstract fun favSongDao () : FavoriteSongDao

    companion object{
        private val FAV_DB_NAME = "favoriteSongDB"
        private var instance: FavoriteSongDB? = null
        private val TAG = FavoriteSongDB::class.java.simpleName

        fun getFavDB(context: Context): FavoriteSongDB{
            return instance ?: synchronized(this){
                instance ?: createDB(context)
            }
        }

        private fun createDB(context: Context): FavoriteSongDB{
            val db = Room.databaseBuilder(context.applicationContext, FavoriteSongDB::class.java, FAV_DB_NAME)
            db.apply {
                addCallback(object : RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "FavoriteSongDB createDB")
                    }
                })
            }
            return db.build()
        }
    }
}