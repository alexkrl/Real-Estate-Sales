package com.alexk.nadlansales.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.alexk.nadlansales.R
import com.alexk.nadlansales.ui.news.NewsFragment
import com.alexk.nadlansales.ui.search_estates.AddressSearchFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(R.layout.main_fragment) {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
        if (savedInstanceState == null) {
            showFragment(AddressSearchFragment.newInstance())
        }
    }


    private fun initBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    showFragment(AddressSearchFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.page_2 -> {
                    showFragment(NewsFragment.newInstance())
                    return@setOnNavigationItemSelectedListener true

                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.content_container, fragment)
            .commit()
    }


}