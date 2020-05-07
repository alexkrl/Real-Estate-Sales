package com.alexk.nadlansales.ui.splash

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.alexk.nadlansales.R


class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        super.onCreate(savedInstanceState)
    }

}
