package com.alexk.nadlansales.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexk.nadlansales.model.entities.Street.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Street(
    @PrimaryKey
    val Key: String,
    val Rank: Int,
    val Value: String
//    val type: Street
)
{
    companion object {
        const val TABLE_NAME = "selected_streets"
    }
}