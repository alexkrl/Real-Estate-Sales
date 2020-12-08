package com.alexk.nadlansales.ui.search_estates

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.data.repos.AddressRepository
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.utils.Addresses
import com.alexk.nadlansales.utils.Coroutines
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@FlowPreview
class AddressSearchViewModel(
    private val addressRepository: AddressRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    private val _addressAutoComplete = MutableLiveData<Addresses>()
    val addressAutoComplete: LiveData<Addresses>
        get() = _addressAutoComplete

    private val testFlow = MutableStateFlow(null)
    private val myTestFlow : MutableStateFlow<Nothing?> = testFlow
//    private val _stateFlowAddress = MutableStateFlow()
//    private val test : StateFlow<Int> = _stateFlowAddress

    private val _countState = MutableStateFlow(0)
    val countState: StateFlow<Int> = _countState

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("ALEX_TAG - AddressSearchViewModel-> ${exception.message}")
    }

    init {

//        _stateFlowAddress.value = State.loading<List<String>>()
        getRecentSelected()
    }

    fun autoCompleteAddress(address: String) {

        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO){
                if (address.isNullOrEmpty()) {
                    getRecentSelected()
                } else {
                    addressRepository.getAutoCompleteAddress(address).collect {
                        if (it is State.Success) {
                            it.data.ifEmpty {
                                _addressAutoComplete.postValue(State.error("Not Found !!! :P"))
                                return@collect
                            }
                        }
                        _addressAutoComplete.postValue(it)
                    }
                }
            }
        }
    }

    /* Save selected street to recent */
    fun saveSelectedStreet(street: Street) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.streetsDAO().insert(street)
        }
    }

    private fun getRecentSelected() {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO){
                _addressAutoComplete.postValue(State.success(appDatabase.streetsDAO().getAll()))
            }
        }
    }
}
