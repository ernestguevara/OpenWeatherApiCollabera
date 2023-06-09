package com.ernestguevara.openweatherapicollabera.presentation.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ernestguevara.openweatherapicollabera.BaseActivity
import com.ernestguevara.openweatherapicollabera.R
import com.ernestguevara.openweatherapicollabera.databinding.ActivityMainBinding
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthActivity
import com.ernestguevara.openweatherapicollabera.presentation.auth.AuthViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()

        weatherViewModel.setEmail(authViewModel.currentUser?.email.toString())

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            weatherViewModel.getWeather()
        }

        askPermissions()

        binding.fab.setOnClickListener {
            showAlertDialog(
                this@MainActivity,
                getString(R.string.logout),
                getString(R.string.label_logout_message),
                getString(R.string.label_ok),
                {
                    authViewModel.logout()
                    startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                },
                getString(R.string.label_cancel)
            )
        }
    }

    private fun setupViewPager() {
        binding.run {
            val tabNames = mutableListOf<String>().apply {
                add(getString(R.string.label_today))
                add(getString(R.string.label_history))
            }

            val fragments = mutableListOf<Fragment>().apply {
                add(WeatherHomeFragment())
                add(WeatherHistoryFragment())
            }

            viewPager.apply {
                adapter = object : FragmentStateAdapter(this@MainActivity) {
                    override fun getItemCount(): Int = tabNames.size

                    override fun createFragment(position: Int): Fragment {
                        return fragments[position]
                    }
                }

                offscreenPageLimit = tabNames.size

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        tabLayout.selectTab(tabLayout.getTabAt(position))
                    }
                })
            }

            tabLayout.apply {
                isTabIndicatorFullWidth = false
                overScrollMode = View.OVER_SCROLL_NEVER
                tabMode = TabLayout.MODE_FIXED

                addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab != null) {
                            viewPager.currentItem = tab.position
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        //do nothing
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        //do nothing
                    }
                })
            }

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabNames[position]
            }.attach()
        }
    }

    fun askPermissions() {
        val fineLocationPermission = Manifest.permission.ACCESS_FINE_LOCATION
        val coarseLocationPermission = Manifest.permission.ACCESS_COARSE_LOCATION

        val fineLocationGranted = ContextCompat.checkSelfPermission(
            this,
            fineLocationPermission
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            this,
            coarseLocationPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineLocationGranted || !coarseLocationGranted) {
            val permissionsToRequest = mutableListOf<String>()
            if (!fineLocationGranted) {
                permissionsToRequest.add(fineLocationPermission)
            }
            if (!coarseLocationGranted) {
                permissionsToRequest.add(coarseLocationPermission)
            }

            permissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            weatherViewModel.getWeather()
        }
    }
}