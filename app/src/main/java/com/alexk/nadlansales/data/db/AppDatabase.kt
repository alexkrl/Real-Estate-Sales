package com.alexk.nadlansales.data.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.entities.Street


/**
 * Created by alexkorolov on 16/03/2020.
 */
@Database(entities = [Street::class, EstateQueryJson::class], version = AppDatabase.DB_VERSION, exportSchema = false)
@TypeConverters(DBConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun streetsDAO(): StreetsDAO
    abstract fun estateJsonQueryDAO(): EstateJsonQueryDAO

    companion object {
        const val DB_VERSION = 5
        const val DB_NAME = "estates_app_database"

        val MIGRATION_1_2: Migration = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.e("TAG", "migrate: ")
            }
        }
    }
}