package com.example.stidyretrofitmoviebase.ui.movieDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stidyretrofitmoviebase.databinding.ActivityDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.extras?.getString("poster") ?: ""
        val id = intent.extras?.getString("id") ?: ""

        binding.viewPager.adapter = DetailsViewPagerAdapter(
            supportFragmentManager,
            lifecycle,
            posterUrl = url,
            posterId = id
        )

        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                if (position == 0) tab.text = "Постер"
                else tab.text = "О фильме"
            }
        tabLayoutMediator.attach()
    }

}