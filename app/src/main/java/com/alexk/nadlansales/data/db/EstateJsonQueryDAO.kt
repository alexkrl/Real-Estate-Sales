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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(street: EstateQueryJson)

    @Delete
    fun delete(street: EstateQueryJson)
}