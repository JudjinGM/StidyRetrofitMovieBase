package com.example.stidyretrofitmoviebase.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.stidyretrofitmoviebase.R
import com.example.stidyretrofitmoviebase.databinding.ActivityRootBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class RootActivity : AppCompatActivity() {

    private var _binding: ActivityRootBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.detailsFragment, R.id.movieCastFragment -> bottomNavigationView.isVisible = false
                else -> bottomNavigationView.isVisible = true
            }

        }
    }

}