package com.dnedev.photoeditor.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dnedev.photoeditor.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
    }

    private fun initNavController() {

        findNavController(R.id.nav_host_fragment).let { navController ->
            toolbar.setupWithNavController(
                navController,
                AppBarConfiguration(findNavController(R.id.nav_host_fragment).graph)
            )

            navController.addOnDestinationChangedListener { _, destination, _ ->
                setSupportActionBar(toolbar)
                when (destination.id) {
                    R.id.homeFragment -> {
                        toolbar.title = getString(R.string.home)
                        supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    }
                }
            }
        }
    }
}