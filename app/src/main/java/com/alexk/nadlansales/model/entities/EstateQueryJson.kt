package com.alexk.nadlansales.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.alexk.nadlansales.data.db.DBConverters
import com.alexk.nadlansales.model.network.estates.Nav
import com.alexk.nadlansales.model.network.estates.QueryMapParams
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class EstateQueryJson(
    val Alert: String?,
    val CurrentLavel: Int,
    val DescLayerID: String?,
    val Distance: Int,
    val FillterRoomNum: Int,
    val GridDisplayType: Int,
    val Gush: String?,
    val MoreAssestsType: Int,
    val MutipuleResults: Boolean,
    @TypeConverters(DBConverters::class)
    val Navs: List<Nav>?,
    val ObjectID: String?,
    val ObjectIDType: String?,
    val ObjectKey: String?,
    val OrderByDescending: Boolean,
    val OrderByFilled: String?,
    @PrimaryKey
    val OriginalSearchString: String,
    var PageNo: Int = 1,
    val Parcel: String?,
    @TypeConverters(DBConverters::class)
    val QueryMapParams: QueryMapParams?,
    val ResultLable: String?,
    val ResultType: Int,
//    val ResultsOptions: Any,
    val X: Double,
    val Y: Double,
    val isHistorical: Boolean,
    val showLotAddress: Boolean,
    val showLotParcel: Boolean
) : Parcelable