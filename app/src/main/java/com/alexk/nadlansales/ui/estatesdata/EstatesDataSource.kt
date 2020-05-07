package com.alexk.nadlansales.ui.estatesdata

import androidx.paging.PageKeyedDataSource
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.model.network.estates.EstateInfo
import com.alexk.nadlansales.model.entities.EstateQueryJson
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
            resp?.body()?.AllResults?.let {
                callback.onResult(it, 1, 2)
            }

        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
        viewModelScope.launch(Dispatchers.IO) {
            estateQueryJson?.PageNo = params.key
            val resp = estateQueryJson?.let { estatesRepository.getData(it) }
            resp?.body()?.AllResults?.let {
                callback.onResult(it, params.key.plus(1))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
//        TODO need to implement this one ? i`m not sure
//        viewModelScope.launch(Dispatchers.IO) {
//            println("ALEX_TAG - EstatesDataSource->loadBefore" + params.key)
//            if (params.key > 1) {
//                estateQueryJson?.PageNo = params.key.minus(1)
//                println("ALEX_TAG - EstatesDataSource->loadBefore ${params.key}")
//                val resp = estateQueryJson?.let { estatesRepository.getData(it) }
//                resp?.body()?.AllResults?.let {
//                    callback.onResult(it, params.key)
//                }
//            }
//        }
    }


}