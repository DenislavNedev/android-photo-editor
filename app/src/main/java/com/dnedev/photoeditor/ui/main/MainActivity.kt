package com.dnedev.photoeditor.ui.main

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dnedev.photoeditor.R
import com.dnedev.photoeditor.utils.RequestQueueUtil
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var requestQueueUtil: RequestQueueUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestQueueUtil.start()
        setContentView(R.layout.activity_main)
        initNavController()
    }

    override fun onDestroy() {
        requestQueueUtil.stop()
        super.onDestroy()
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
                    R.id.editFragment -> {
                        toolbar.title = getString(R.string.edit_photo)
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }
}