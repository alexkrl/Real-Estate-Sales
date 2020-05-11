package com.alexk.nadlansales.ui.estatesdata

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.network.estates.EstateInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by alexkorolov on 21/04/2020.
 */
class EstatesDataSource(
    private val estatesRepository: EstatesRepository,
    private val addressQuery: String,
    private val viewModelScope: CoroutineScope
) : PageKeyedDataSource<Int, EstateInfo>() {

    private var estateQueryJson: EstateQueryJson? = null


    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, EstateInfo>) {
        viewModelScope.launch(Dispatchers.IO) {
            estateQueryJson = estatesRepository.getJsonData(addressQuery)
            val resp = estateQueryJson?.let { estatesRepository.getData(it) }
            resp?.body()?.SpecificAddressData?.let {
                callback.onResult(it, 1, 2)
                return@launch
            }
            resp?.body()?.AllResults?.let {
                callback.onResult(it, 1, 2)
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
        viewModelScope.launch(Dispatchers.IO) {
            estateQueryJson?.PageNo = params.key
            val resp = estateQueryJson?.let { estatesRepository.getData(it) }

            resp?.body()?.SpecificAddressData?.let {
                Log.e("EstatesDataSource", "SpecificAddressData loadInitial: ${estateQueryJson.toString()}")
                callback.onResult(it, params.key.plus(1))
                return@launch
            }

            resp?.body()?.AllResults?.let {
                Log.e("EstatesDataSource", "loadAfter: ${estateQueryJson.toString()}")
                callback.onResult(it, params.key.plus(1))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
//        TODO need to implement this one ? i`m not sure
    }


}