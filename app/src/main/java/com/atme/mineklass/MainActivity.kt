package com.atme.mineklass

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.atme.mineklass.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout


        val navController = findNavController(R.id.navHostFragment)

        setSupportActionBar(binding.toolbar)
        binding.navigationDrawer.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navigationDrawer, navController)

        // Make sure that navigation drawer only opens in the title page.
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            if (controller.graph.startDestinationId != destination.id) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                binding.toolbar.visibility = Toolbar.VISIBLE
                binding.navButton.visibility = ImageView.GONE
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.toolbar.visibility = Toolbar.GONE
                binding.navButton.visibility = ImageView.VISIBLE
            }
        }

        binding.navButton.setOnClickListener {
            binding.drawerLayout.open()
        }

    }

    override fun onSupportNavigateUp(): Boolean {

        val navController = findNavController(R.id.navHostFragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}