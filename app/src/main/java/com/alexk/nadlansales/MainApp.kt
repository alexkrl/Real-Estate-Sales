package com.alexk.nadlansales

import android.app.Application
import com.alexk.nadlansales.di.mainModule
import org.koin.android.ext.android.startKoin

/**
 * Created by alexkorolov on 11/03/2020.
 */
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this,
            listOf(mainModule),
            loadPropertiesFromFile = true)
    }

}