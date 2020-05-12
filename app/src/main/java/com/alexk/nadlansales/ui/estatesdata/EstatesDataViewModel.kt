package com.alexk.nadlansales.ui.estatesdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.model.network.estates.EstateInfo

class EstatesDataViewModel(private val estatesRepository: EstatesRepository) : ViewModel() {

    private var postsLiveData: LiveData<PagedList<EstateInfo>>

    var queryAddress = ""

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(false)
            .build()

        postsLiveData = initializedPagedListBuilder(config)
            .setInitialLoadKey(1)
            .build()

    }

    val estatesDataList
        get() = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, EstateInfo> {
        val dataSourceFactory = object : DataSource.Factory<Int, EstateInfo>() {
            override fun create() = EstatesDataSource(estatesRepository, queryAddress, viewModelScope)
        }

        return LivePagedListBuilder(dataSourceFactory, config)
    }
}
