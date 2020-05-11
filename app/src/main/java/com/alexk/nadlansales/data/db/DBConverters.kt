package com.alexk.nadlansales.data.db

import androidx.room.TypeConverter
import com.alexk.nadlansales.model.network.estates.Nav
import com.alexk.nadlansales.model.network.estates.QueryMapParams
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


/**
 * Created by alexkorolov on 07/05/2020.
 */
class DBConverters {

    @TypeConverter
    fun stringToQueryMapParams(data: String?): QueryMapParams? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<QueryMapParams?>() {}.type
        return gson.fromJson<QueryMapParams>(data, listType)
    }

    @TypeConverter
    fun queryMapParamsToString(queryMapParams: QueryMapParams?): String? {
        val gson = Gson()
        return gson.toJson(queryMapParams)
    }

    @TypeConverter
    fun stringToNavList(data: String?): List<Nav>? {
        val gson = Gson()
        if (data == null) {
            return null
        }
        val listType: Type = object : TypeToken<List<Nav>?>() {}.type
        return gson.fromJson<List<Nav>>(data, listType)
    }

    @TypeConverter
    fun navListToString(navList: List<Nav>?): String? {
        val gson = Gson()
        return gson.toJson(navList)
    }
}