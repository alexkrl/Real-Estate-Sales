package com.alexk.nadlansales

import android.content.Context
import com.alexk.nadlansales.data.DataRepository
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.data.network.NetworkConnectionInterceptor
import com.alexk.nadlansales.ui.adapters.EstatesHistoryDataAdapter
import com.alexk.nadlansales.ui.viewmodel.AddressSearchViewModel
import com.alexk.nadlansales.ui.viewmodel.SalesDataViewModel
import com.alexk.nadlansales.utils.AppConsts.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by alexkorolov on 11/03/2020.
 */

val mainModule = module {

    single { createWebService() }
    single { getDatabase(get()) }
    single { DataRepository(get(), get()) }
    single { NetworkConnectionInterceptor(get()) }

    single { EstatesHistoryDataAdapter() }

    viewModel { AddressSearchViewModel(get(), get()) }
    viewModel { SalesDataViewModel() }

}

fun createWebService(): EstateApi { // networkConnectionInterceptor: NetworkConnectionInterceptor - removed from constructor for now

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
//    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()


    val okkHttpclient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    return Retrofit.Builder()
        .client(okkHttpclient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EstateApi::class.java)
}

fun getDatabase(context: Context): AppDatabase {
    return AppDatabase.getInstance(context);
}