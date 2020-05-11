package com.alexk.nadlansales.data.db

import androidx.room.*
import com.alexk.nadlansales.model.entities.EstateQueryJson


/**
 * Created by alexkorolov on 16/03/2020.
 */
@Dao
interface EstateJsonQueryDAO {

    @Query("SELECT * FROM EstateQueryJson where OriginalSearchString = :query")
    fun getQueryJson(query : String): EstateQueryJson

    @Query("SELECT * FROM EstateQueryJson")
    fun getAllQueryJson(): List<EstateQueryJson>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(street: EstateQueryJson)

    @Delete
    fun delete(street: EstateQueryJson)
}