package com.alexk.nadlansales.ui.estatesdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.model.network.estates.EstateInfo

class EstatesDataViewModel(private val estatesRepository: EstatesRepository) : ViewModel() {

    var queryAddress = ""

    val estatesPageData: LiveData<PagedList<EstateInfo>> by lazy {
        estatesRepository.getLiveDataPageList(queryAddress, viewModelScope)
    }

    val networkState : LiveData<State<String>> by lazy {
        estatesRepository.getLiveDataState()
    }
}
