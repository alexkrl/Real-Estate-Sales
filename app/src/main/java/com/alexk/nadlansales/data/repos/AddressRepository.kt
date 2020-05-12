package com.alexk.nadlansales.data.repos

import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.data.network.State
import com.alexk.nadlansales.model.entities.Street
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Created by alexkorolov on 21/04/2020.
 */
class AddressRepository(private val estateApi: EstateApi, private val appDatabase: AppDatabase) {

    fun getAutoCompleteAddress(query: String) = flow {
        emit(State.loading<List<Street>>())

        val request = estateApi.getAutoCompleteAddress(query)
        if (request.isSuccessful) {
            val autoCompleteResponse = request.body()

            val estateAddresses = mutableListOf<Street>()
            autoCompleteResponse?.res?.run { ->

                if (!streets.isNullOrEmpty()) {
                    estateAddresses.addAll(streets)
                }

                if (!settlement.isNullOrEmpty()) {
                    estateAddresses.addAll(settlement)
                }

                if (!gushim.isNullOrEmpty()) {
                    estateAddresses.addAll(gushim)
                }
            }
            val retVal: List<Street> = estateAddresses
            emit(State.success(retVal))
        } else {
            emit(State.error<List<Street>>(request.message()))
        }
    }.flowOn(Dispatchers.IO)
}