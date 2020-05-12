package com.alexk.nadlansales.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {

            println("Make sure you have an active data connection")

            val mediaType = "text/html; charset=utf-8".toMediaType()
            val message = "No Internet Connection !"
            val content = ""
            val responseBody = ResponseBody.create(mediaType, content)

            return Response.Builder()
                .code(404)
                .protocol(Protocol.HTTP_2)
                .message(message)
                .body(responseBody)
                .request(chain.request())
                .build()

        } else {
            println("ALEX_TAG - NetworkConnectionInterceptor->intercept proceed request")
        }
        return chain.proceed(chain.request())

    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

}