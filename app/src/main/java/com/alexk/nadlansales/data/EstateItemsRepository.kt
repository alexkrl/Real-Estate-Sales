package com.alexk.nadlansales.data

import androidx.paging.PageKeyedDataSource
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.model.network.esates.EstateInfo
import com.alexk.nadlansales.model.network.esates.EstateQueryJson
import com.alexk.nadlansales.utils.Coroutines

/**
 * Created by alexkorolov on 11/03/2020.
 */
class EstateItemsRepository(
    private val estateApi: EstateApi,
    private val queryJson: EstateQueryJson
) :
    PageKeyedDataSource<String, EstateInfo>() {

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, EstateInfo>
    ) {
        Coroutines.io {
            val response = estateApi.getEstatesData(queryJson)
            when{
                response.isSuccessful -> {
                    val listing = response.body()?.AllResults
                    callback.onResult(listing as MutableList<EstateInfo>,
                        queryJson.PageNo.toString(),
                        queryJson.PageNo.plus(1).toString())
                }
            }
        }
    }

    override fun loadAfter(
        params: LoadParams<String>,
        callback: LoadCallback<String, EstateInfo>
    ) {
        queryJson.PageNo++
        Coroutines.io {

            val response = estateApi.getEstatesData(queryJson)
            when{
                response.isSuccessful -> {
                    val listing = response.body()?.AllResults
                    callback.onResult(listing as MutableList<EstateInfo>, queryJson.PageNo.toString())
                }
            }
        }
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, EstateInfo>
    ) {
        queryJson.PageNo--
        Coroutines.io {
            val response = estateApi.getEstatesData(queryJson)

            when{
                response.isSuccessful -> {
                    val listing = response.body()?.AllResults
                    callback.onResult(listing as MutableList<EstateInfo>, queryJson.PageNo.toString())
                }
            }
        }
    }
}