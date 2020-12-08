package com.alexk.nadlansales.ui.main

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexk.nadlansales.ui.activity.LaunchActivity
import com.alexk.nadlansales.ui.news.NewsFragment
import com.alexk.nadlansales.ui.search_estates.AddressSearchFragment

class MainPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private var searchFragment : AddressSearchFragment? = null
    private var newsFragment : NewsFragment? = null

    init {
        searchFragment = AddressSearchFragment()
        newsFragment = NewsFragment()
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return searchFragment as Fragment
            1 -> return newsFragment as Fragment
        }
        return newsFragment as Fragment
    }

}