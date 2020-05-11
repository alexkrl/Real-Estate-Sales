package com.alexk.nadlansales.di

import android.content.Context
import androidx.room.Room
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.db.AppDatabase.Companion.MIGRATION_1_2
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.data.repos.AddressRepository
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.ui.estatesdata.EstatesHistoryDataAdapter
import com.alexk.nadlansales.ui.estatesdata.SalesDataViewModel
import com.alexk.nadlansales.ui.searchaddress.AddressSearchViewModel
import com.alexk.nadlansales.ui.splash.SplashViewModel
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
    single { EstatesRepository(get(), get()) }
    single { AddressRepository(get(), get()) }
//    single { NetworkConnectionInterceptor(get()) }
//    single { EstatesDataSource(get()) }
    single { EstatesHistoryDataAdapter() }

    viewModel { AddressSearchViewModel(get(), get()) }
    viewModel { SalesDataViewModel(get()) }
    viewModel { SplashViewModel() }


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
    synchronized(context) {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }
}