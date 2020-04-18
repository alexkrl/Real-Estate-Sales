package com.alexk.nadlansales.data.network


import com.alexk.nadlansales.model.network.AutoCompleteResponse
import com.alexk.nadlansales.model.network.SalesResponse
import com.alexk.nadlansales.model.network.esates.EstateQueryJson
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EstateApi {

    @GET("TldSearch//api/AutoComplete")
    fun getAutoCompleteAddress(@Query("query") query: String): Call<AutoCompleteResponse>

    @GET("Nadlan.REST/Main/GetDataByQuery")
    fun getEstatesJson(@Query("query") query: String): Call<EstateQueryJson>

    @POST("Nadlan.REST/Main/GetAssestAndDeals")
    suspend fun getEstatesData(@Body body: EstateQueryJson): Response<SalesResponse>


}

