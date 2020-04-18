package com.alexk.nadlansales.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexk.nadlansales.data.DataRepository
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.model.entities.Street
import com.alexk.nadlansales.model.network.esates.EstateQueryJson
import com.alexk.nadlansales.utils.Coroutines

class AddressSearchViewModel(
    private val dataRepository: DataRepository,
    private val appDatabase: AppDatabase
) : ViewModel() {

    var addressAutoComplete: MutableLiveData<List<Street>> = MutableLiveData()
    var dataJson: MutableLiveData<EstateQueryJson> = MutableLiveData()


    fun autoCompleteAddress(address: String) {
        dataRepository.getAutoCompleteAddress(address, addressAutoComplete)
    }

    fun getAddressJson(address: String) {
        dataRepository.getJsonData(address, dataJson)
    }

    /* Save selected street to recent */
    fun saveSelectedStreet(street: Street) {
        Coroutines.io {
            appDatabase.streetsDAO().insert(street)
        }
    }

    fun getRecentSelected() {
        Coroutines.io {
            addressAutoComplete.postValue(appDatabase.streetsDAO().getAll())
        }
    }
}
