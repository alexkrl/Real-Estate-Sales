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

        // Write a message to the database
//        println("ALEX_TAG - MainApp->onCreate init DB")
//        val database = FirebaseDatabase.getInstance()
//        val myRef = database.getReference("estateTest")
//        myRef.setValue(EstateInfo(
//            ASSETROOMNUM = "",
//            BUILDINGFLOORS = "",
//            BUILDINGYEAR = "",
//            DEALAMOUNT = "",
//            DEALDATE = "",
//            DEALDATETIME = "",
//            DEALNATURE = "",
//            DEALNATUREDESCRIPTION = "",
//            DISPLAYADRESS = "",
//            FLOORNO = "",
//            FULLADRESS = "",
//            GUSH = "",
//            KEYVALUE = "",
//            NEWPROJECTTEXT = "",
//            POLYGON_ID = "",
//            PROJECTNAME = "",
//            TREND_FORMAT = "",
//            TREND_IS_NEGATIVE = false,
//            TYPE = 123,
//            YEARBUILT = ""
//        ))
    }

}