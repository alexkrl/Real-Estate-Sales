package com.alexk.nadlansales.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alexk.nadlansales.data.EstateItemsRepository
import com.alexk.nadlansales.model.network.esates.EstateInfo
import com.alexk.nadlansales.model.network.esates.EstateQueryJson

class SalesDataViewModel: ViewModel() {

    var estatesData: MutableLiveData<List<EstateInfo>> = MutableLiveData()

    private var postsLiveData : LiveData<PagedList<EstateInfo>>

    var estateItemsRepository : EstateItemsRepository? = null




    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

//    fun getEstatesData(jsonTest : EstateQueryJson){
//        estateItemsRepository?.getEstateDataByAddress(jsonTest, postsLiveData)
//            object : DataRepository.OnProductData {
//            override fun <T> onSuccess(response: Response<T>) {
//                val response: Response<SalesResponse> = response as Response<SalesResponse>
//                estatesData.value = response.body()?.AllResults
//            }
//
//            override fun onFailure() {}
//        })
//    }

    fun getPosts():LiveData<PagedList<EstateInfo>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<String, EstateInfo> {

        val dataSourceFactory = object : DataSource.Factory<String, EstateInfo>() {
            override fun create(): EstateItemsRepository? {
                return estateItemsRepository
            }
        }
        return LivePagedListBuilder<String, EstateInfo>(dataSourceFactory, config)
    }


}