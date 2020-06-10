package com.alexk.nadlansales.di

import android.content.Context
import androidx.room.Room
import com.alexk.nadlansales.data.db.AppDatabase
import com.alexk.nadlansales.data.network.EstateApi
import com.alexk.nadlansales.data.network.NetworkConnectionInterceptor
import com.alexk.nadlansales.data.repos.AddressRepository
import com.alexk.nadlansales.data.repos.EstatesRepository
import com.alexk.nadlansales.ui.estatesdata.EstatesDataViewModel
import com.alexk.nadlansales.ui.estatesdata.EstatesHistoryDataAdapter
import com.alexk.nadlansales.ui.search_estates.AddressSearchViewModel
import com.alexk.nadlansales.ui.splash.SplashViewModel
import com.alexk.nadlansales.utils.AppConsts.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


/**
 * Created by alexkorolov on 11/03/2020.
 */

val mainModule = module {

    single { createWebService(get()) }
    single { getDatabase(get()) }
    single { EstatesRepository(get(), get()) }
    single { AddressRepository(get(), get()) }
    single { NetworkConnectionInterceptor(get()) }
//    single { EstatesDataSource(get()) }
    single { EstatesHistoryDataAdapter() }

    viewModel { AddressSearchViewModel(get(), get()) }
    viewModel { EstatesDataViewModel(get()) }
    viewModel { SplashViewModel() }


}

fun createWebService(context: Context): EstateApi {
// networkConnectionInterceptor: NetworkConnectionInterceptor - removed from constructor for now

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val networkConnectionInterceptor = NetworkConnectionInterceptor(context)

    val okkHttpclient = getUnsafeOkHttpClient()?.let {
        it.addInterceptor(interceptor)
        it.addInterceptor(networkConnectionInterceptor)
        it.build()
    }


    return Retrofit.Builder()
        .client(okkHttpclient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EstateApi::class.java)
}

// User unsafe httpclient because of server certificate (not my server :P)
// TODO - Check/Find better solution
private fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
    return try {

        val trustAllCerts = getAllCertificates()
        val sslSocketFactory = getSslFactory(trustAllCerts)

        return buildClient(trustAllCerts, sslSocketFactory)

    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

fun getAllCertificates(): Array<X509TrustManager> {
    return arrayOf(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return arrayOf()
        }
    })
}

fun getSslFactory(trustAllCerts: Array<X509TrustManager>): SSLSocketFactory {
    // Install the all-trusting trust manager
    val sslContext: SSLContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())

    return sslContext.socketFactory
}

fun buildClient(trustAllCerts: Array<X509TrustManager>, sslSocketFactory: SSLSocketFactory): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0])
        .hostnameVerifier(HostnameVerifier { hostname, session -> true })

    return builder
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