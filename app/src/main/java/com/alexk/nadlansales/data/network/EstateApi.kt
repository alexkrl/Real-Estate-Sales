package com.alexk.nadlansales.data.network


import com.alexk.nadlansales.model.entities.EstateQueryJson
import com.alexk.nadlansales.model.network.AutoCompleteResponse
import com.alexk.nadlansales.model.network.SalesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EstateApi {

    @GET("TldSearch//api/AutoComplete")
    suspend fun getAutoCompleteAddress(@Query("query") query: String): Response<AutoCompleteResponse>

    @GET("Nadlan.REST/Main/GetDataByQuery")
    suspend fun getEstatesJson(@Query("query") query: String): Response<EstateQueryJson>

    @POST("Nadlan.REST/Main/GetAssestAndDeals")
    suspend fun getEstatesData(@Body body: EstateQueryJson): Response<SalesResponse>


}

