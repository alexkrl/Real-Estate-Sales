package com.alexk.nadlansales.data.repos

import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.network.SalesResponse
import retrofit2.Response

/**
 * Created by alexkorolov on 11/03/2020.
 */
class EstatesRepository(private val estateApi: EstateApi, private val appDatabase: AppDatabase) {

    suspend fun getJsonData(query: String): EstateQueryJson? {
        var retVal: EstateQueryJson?
        retVal = getJsonDataFromDB(query)
        if (retVal == null) {
            val queryResponse = estateApi.getEstatesJson(query)
            val queryJson = queryResponse.body()
            queryJson?.PageNo = 1
            queryJson?.let { saveJsonToDB(it) }

            retVal = queryJson
        }
        return retVal
    }


    suspend fun getData(estateQueryJson: EstateQueryJson): Response<SalesResponse> {
        return estateApi.getEstatesData(estateQueryJson)
    }

    private fun getJsonDataFromDB(query: String): EstateQueryJson? {
        return appDatabase.estateJsonQueryDAO().getQueryJson(query)
    }

    private fun saveJsonToDB(queryJson: EstateQueryJson) {
        appDatabase.estateJsonQueryDAO().insert(queryJson)
    }
}