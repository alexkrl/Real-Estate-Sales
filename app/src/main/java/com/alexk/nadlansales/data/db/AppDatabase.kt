package com.alexk.nadlansales.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.entities.Street


/**
 * Created by alexkorolov on 16/03/2020.
 */
@Database(entities = [Street::class, EstateQueryJson::class], version = AppDatabase.DB_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun streetsDAO(): StreetsDAO
    abstract fun estateJsonQueryDAO(): EstateJsonQueryDAO

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "foodium_database"
    }
}