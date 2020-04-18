package com.alexk.nadlansales.ui


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.alexk.nadlansales.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("ALEX_TAG - MainActivity->onCreate $savedInstanceState")
        if (savedInstanceState == null) {
            val host = NavHostFragment.create(R.navigation.estate_nav_graph)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, host)
                .setPrimaryNavigationFragment(host)
                .commit()
        }

//        myTests()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("ALEX_TAG - MainActivity->onBackPressed")
        findNavController(R.id.fragment_container_view).navigateUp()
    }
}
