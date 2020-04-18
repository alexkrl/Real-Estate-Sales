package com.alexk.nadlansales.data.db

import androidx.room.*
import com.alexk.nadlansales.model.entities.Street


/**
 * Created by alexkorolov on 16/03/2020.
 */
@Dao
interface StreetsDAO {

    @Query("SELECT * FROM ${Street.TABLE_NAME}")
    fun getAll(): List<Street>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(street: Street)

    @Delete
    fun delete(street: Street)
}