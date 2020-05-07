package com.alexk.nadlansales.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Street(
    @PrimaryKey
    val Key: String,
    val Rank: Int,
    val Value: String
) : Parcelable