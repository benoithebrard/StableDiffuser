package com.example.stablediffuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.search_dest, R.id.favorites_dest)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.provideAppContext = { applicationContext }

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        setupActionBarWithNavController(navController, appBarConfiguration)

        with(viewBinding.bottomNavView) {
            NavigationUI.setupWithNavController(this, navController)

            setOnItemSelectedListener { item ->
                // This is needed for proper tab selection
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }

            setOnItemReselectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.search_dest -> {
                        // Search tab was reselected, pop all fragments added to that stack
                        navController.popBackStack(
                            destinationId = R.id.search_dest,
                            inclusive = false
                        )
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}