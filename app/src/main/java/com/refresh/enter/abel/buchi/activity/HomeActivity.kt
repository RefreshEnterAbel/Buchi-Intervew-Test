package com.refresh.enter.abel.buchi.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.refresh.enter.abel.buchi.R
import com.refresh.enter.abel.buchi.databinding.ActivityHomeBinding
import com.refresh.enter.abel.buchi.viewmode.HomeActivityViewModel


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var mViewModel: HomeActivityViewModel? = null

    private val navController: NavController
        get() {
            return findNavController(R.id.nav_host_home)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]

        mViewModel!!.setHomeSection()
        val navView: BottomNavigationView = binding.navView

        navView.setupWithNavController(navController)

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    viewFragment(R.id.navigation_home)
                }
                R.id.navigation_search -> {
                    viewFragment(R.id.navigation_search)
                }
            }
            false
        }
    }


    private fun viewFragment(to: Int) {
        navController.navigate(to)
    }
}

