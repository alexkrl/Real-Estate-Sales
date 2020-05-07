package com.alexk.nadlansales.ui.searchaddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.repos.AddressRepository
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.utils.Addresses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class AddressSearchViewModel(
    private val addressRepository: AddressRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    var addressAutoComplete: MutableLiveData<Addresses> = MutableLiveData()

    fun autoCompleteAddress(address: String) {
        viewModelScope.launch(Dispatchers.IO) {
            addressRepository.getAutoCompleteAddress(address).collect {
                if (it is State.Success){
                    it.data.ifEmpty {
                        addressAutoComplete.postValue(State.error("Not Found !!! :P"))
                        return@collect
                    }
                }
                addressAutoComplete.postValue(it)
            }
        }
    }

    /* Save selected street to recent */
    fun saveSelectedStreet(street: Street) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.streetsDAO().insert(street)
        }
    }

    fun getRecentSelected() {
        viewModelScope.launch(Dispatchers.IO) {
            addressAutoComplete.postValue(State.success(appDatabase.streetsDAO().getAll()))
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("ALEX_TAG - AddressSearchViewModel->onCleared")
    }
}
