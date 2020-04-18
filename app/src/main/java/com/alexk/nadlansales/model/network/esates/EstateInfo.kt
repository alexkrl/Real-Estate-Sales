package com.alexk.nadlansales.model.network.esates

import java.io.Serializable

data class EstateInfo(
    val ASSETROOMNUM: String,
    val BUILDINGFLOORS: String,
    val BUILDINGYEAR: String,
    val DEALAMOUNT: String,
    val DEALDATE: String,
    val DEALDATETIME: String,
    val DEALNATURE: String,
    val DEALNATUREDESCRIPTION: String,
    val DISPLAYADRESS: String,
    val FLOORNO: String,
    val FULLADRESS: String,
    val GUSH: String,
    val KEYVALUE: String,
    val NEWPROJECTTEXT: String,
    val POLYGON_ID: String,
    val PROJECTNAME: Any,
    val TREND_FORMAT: String,
    val TREND_IS_NEGATIVE: Boolean,
    val TYPE: Int,
    val YEARBUILT: String
) : Serializable