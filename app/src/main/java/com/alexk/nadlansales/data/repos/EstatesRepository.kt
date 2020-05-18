package com.alexk.nadlansales.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.network.SalesResponse
import com.alexk.nadlansales.model.network.estates.EstateInfo
import com.alexk.nadlansales.ui.estatesdata.EstatesDataSource
import kotlinx.coroutines.CoroutineScope
import retrofit2.Response

/**
 * Created by alexkorolov on 11/03/2020.
 */
class EstatesRepository(private val estateApi: EstateApi, private val appDatabase: AppDatabase) {

    private lateinit var estatesInfoLiveData: LiveData<PagedList<EstateInfo>>
    private var networkState: MutableLiveData<State<String>> = MutableLiveData()

    fun getLiveDataPageList(queryAddress: String, viewModelScope: CoroutineScope): LiveData<PagedList<EstateInfo>> {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        val dataSourceFactory = object : DataSource.Factory<Int, EstateInfo>() {
            override fun create() = EstatesDataSource(this@EstatesRepository, queryAddress, viewModelScope, networkState)
        }

        estatesInfoLiveData = LivePagedListBuilder(dataSourceFactory, config)
            .setInitialLoadKey(1)
            .build()

        return estatesInfoLiveData
    }

    /* Get JsonQuery data
    * first try to get from DB
    * then if not result will go to api get it and save to DB*/
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

    fun getLiveDataState(): LiveData<State<String>> {
        return networkState
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