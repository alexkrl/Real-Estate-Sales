package com.alexk.nadlansales.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.model.network.AutoCompleteResponse
import com.alexk.nadlansales.model.network.esates.EstateInfo
import com.alexk.nadlansales.model.network.esates.EstateQueryJson
import com.alexk.nadlansales.utils.Coroutines
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by alexkorolov on 11/03/2020.
 */
class DataRepository(private val estateApi: EstateApi, private val appDatabase: AppDatabase) :
    PageKeyedDataSource<String, EstateInfo>(){

    init {
        getRecentAddresses()
    }

    fun getAutoCompleteAddress(
        query: String,
        addressAutoComplete: MutableLiveData<List<Street>>
    ) {

        estateApi.getAutoCompleteAddress(query).enqueue(object : Callback<AutoCompleteResponse>{
            override fun onFailure(call: Call<AutoCompleteResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<AutoCompleteResponse>,
                response: Response<AutoCompleteResponse>
            ) {
                response.body()?.res?.run { ->
                    val estateAddresses = mutableListOf<Street>()

                    if (!streets.isNullOrEmpty()){
                        estateAddresses.addAll(streets)
                    }

                    if (!settlement.isNullOrEmpty()){
                        estateAddresses.addAll(settlement)
                    }

                    if (!gushim.isNullOrEmpty()){
                        estateAddresses.addAll(gushim)
                    }
                    addressAutoComplete.value = estateAddresses
                }

        }
        })
    }

    private fun getRecentAddresses(){
        // TODO
//        Coroutines.io {
//            val recent = appDatabase.streetsDAO().getAll()
//        }
    }

    fun getJsonData(
        query: String,
        dataJson: MutableLiveData<EstateQueryJson>
    ){
        estateApi.getEstatesJson(query).enqueue(object : Callback<EstateQueryJson>{
            override fun onFailure(call: Call<EstateQueryJson>, t: Throwable) {
//                onProductData.onFailure()
            }

            override fun onResponse(
                call: Call<EstateQueryJson>,
                response: Response<EstateQueryJson>
            ) {
                response.body().let {
                    it?.PageNo = 1
                    dataJson.value = it
                }
            }
        })
    }

    // TODO
//    fun getEstateDataByAddress(
//        queryJson: EstateQueryJson,
//        estatesData: LiveData<PagedList<EstateInfo>>
//    ){
//        estateApi.getEstatesData(queryJson).enqueue(object : Callback<SalesResponse>{
//            override fun onFailure(call: Call<SalesResponse>, t: Throwable) {
////                onProductData.onFailure()
//            }
//
//            override fun onResponse(
//                call: Call<SalesResponse>,
//                response: Response<SalesResponse>
//            ) {
////                onProductData.onSuccess(response)
//
//            }
//        })
//    }

//    interface OnProductData {
//        fun <T : Any?> onSuccess(response: Response<T>)
//        fun onFailure()
//    }

    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, EstateInfo>
    ) {

        println("ALEX_TAG - DataRepository->loadInitial")
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, EstateInfo>) {
        println("ALEX_TAG - DataRepository->loadAfter")
    }

    override fun loadBefore(
        params: LoadParams<String>,
        callback: LoadCallback<String, EstateInfo>
    ) {
        println("ALEX_TAG - DataRepository->loadBefore")
    }
}