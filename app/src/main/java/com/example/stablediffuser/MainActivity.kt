package com.example.stablediffuser

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.stablediffuser.config.Configuration
import com.example.stablediffuser.databinding.ActivityMainBinding
import com.example.stablediffuser.network.repositories.FavoritesRepository
import com.example.stablediffuser.utils.HorizontalDrawerListener

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    private val favoritesRepository: FavoritesRepository by lazy {
        Configuration.favoritesRepository
    }

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment_activity_main)
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.search_dest, R.id.favorites_dest),
            drawerLayout = viewBinding.root
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.provideAppContext = { applicationContext }

        viewBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(viewBinding.root)

        setupActionBarWithNavController(navController, appBarConfiguration)

        viewBinding.setupUI()
    }

    override fun onStart() {
        super.onStart()
        favoritesRepository.restore()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    private fun ActivityMainBinding.setupUI() {
        bottomNavView.apply {
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

        sideNavView.apply {
            NavigationUI.setupWithNavController(this, navController)
        }

        root.apply {
            addDrawerListener(HorizontalDrawerListener(viewBinding.contentContainer))
            setScrimColor(Color.TRANSPARENT)
        }
    }

    override fun onStop() {
        favoritesRepository.save()
        super.onStop()
    }
}