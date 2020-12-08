package com.alexk.nadlansales.ui.estatesdata

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.network.SalesResponse
import com.alexk.nadlansales.model.network.estates.EstateInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

/**
 * Created by alexkorolov on 21/04/2020.
 */
class EstatesDataSource(
    private val estatesRepository: EstatesRepository,
    private val addressQuery: String,
    private val viewModelScope: CoroutineScope,
    private val networkState: MutableLiveData<State<String>>
) : PageKeyedDataSource<Int, EstateInfo>() {

    private var estateQueryJson: EstateQueryJson? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, EstateInfo>) {
        viewModelScope.launch(Dispatchers.IO) {
            networkState.postValue(State.loading())
            estateQueryJson = estatesRepository.getJsonData(addressQuery)
            val resp = estateQueryJson?.let { estatesRepository.getData(it) }
            val estateList = prepareEstateList(resp)
            println("ALEX_TAG - EstatesDataSource->loadInitial ${estateList.size}")

            networkState.postValue(State.loadingFinish(estateList.isNullOrEmpty()))

            callback.onResult(estateList, 1, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
        viewModelScope.launch(Dispatchers.IO) {
            estateQueryJson?.PageNo = params.key
            val resp = estateQueryJson?.let { estatesRepository.getData(it) }
            val estateList = prepareEstateList(resp)
            println("ALEX_TAG - EstatesDataSource->loadAfter ${estateList.size}")
            callback.onResult(estateList, params.key.plus(1))
        }
    }

    fun prepareEstateList(resp: Response<SalesResponse>?): ArrayList<EstateInfo> {
        val retList = arrayListOf<EstateInfo>()
        resp?.body()?.SpecificAddressData?.let {
            retList.addAll(it)
            println("ALEX_TAG - EstatesDataSource->prepareEstateList--->Specific")
            return retList
        }

        resp?.body()?.AllResults?.let {
            retList.addAll(it)
            println("ALEX_TAG - EstatesDataSource->prepareEstateList--->All")
            return retList
        }

        return retList
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, EstateInfo>) {
//        TODO need to implement this one ? i`m not sure
    }
}
