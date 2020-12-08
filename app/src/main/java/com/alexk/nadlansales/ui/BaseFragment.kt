package com.alexk.nadlansales.ui

import android.os.Bundle
import androidx.fragment.app.Fragment


/**
 * Created by alexkorolov on 16/04/2020.
 */
abstract class BaseFragment(fragmentLayout: Int) : Fragment(fragmentLayout) {

    abstract fun setTitle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle()
    }
}