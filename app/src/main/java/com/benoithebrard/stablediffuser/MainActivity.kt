package com.benoithebrard.stablediffuser

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.benoithebrard.stablediffuser.databinding.ActivityMainBinding
import com.benoithebrard.stablediffuser.network.repositories.FavoritesRepository
import com.benoithebrard.stablediffuser.utils.HorizontalDrawerListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

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

    override fun onStop() {
        favoritesRepository.save()
        super.onStop()
    }

    private fun ActivityMainBinding.setupUI() {
        setupBottomTab()
        setupNavigationDrawer()
    }

    private fun ActivityMainBinding.setupBottomTab() {
        bottomNavView.apply {
            NavigationUI.setupWithNavController(this, navController)

            // Setup bottom tab selection
            setOnItemSelectedListener { item ->
                NavigationUI.onNavDestinationSelected(item, navController)
                true
            }

            // Setup bottom tab reselection to come back to root
            setOnItemReselectedListener { menuItem ->
                navController.popBackStack(
                    destinationId = menuItem.itemId,
                    inclusive = false
                )
            }
        }
    }

    private fun ActivityMainBinding.setupNavigationDrawer() {
        sideNavView.apply {
            NavigationUI.setupWithNavController(this, navController)
        }

        // Setup navigation drawer
        root.apply {
            addDrawerListener(HorizontalDrawerListener(contentContainer))
            setScrimColor(Color.TRANSPARENT)
        }
    }
}
