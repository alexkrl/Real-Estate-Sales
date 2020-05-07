package com.alexk.nadlansales.model.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexk.nadlansales.model.network.estates.Nav
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class EstateQueryJson(
    val Alert: String,
    val CurrentLavel: Int,
    val DescLayerID: String,
    val Distance: Int,
    val FillterRoomNum: Int,
    val GridDisplayType: Int,
    val Gush: String,
    val MoreAssestsType: Int,
    val MutipuleResults: Boolean,
//    val Navs: List<Nav>,
    @PrimaryKey
    val ObjectID: String,
    val ObjectIDType: String,
    val ObjectKey: String,
    val OrderByDescending: Boolean,
    val OrderByFilled: String,
    val OriginalSearchString: String,
    var PageNo: Int = 1,
    val Parcel: String,
//    val QueryMapParams: QueryMapParams,
    val ResultLable: String,
    val ResultType: Int,
//    val ResultsOptions: Any,
    val X: Double,
    val Y: Double,
    val isHistorical: Boolean,
    val showLotAddress: Boolean,
    val showLotParcel: Boolean
) : Parcelable