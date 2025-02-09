package com.ipro.newsly

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ipro.newsly.databinding.ActivityMainBinding
import com.ipro.newsly.core.datastore.data.manager.LocalUserManagerImpl
import com.ipro.newsly.core.datastore.domain.manager.LocalUserManager
import com.ipro.newsly.feature.onboarding.presentation.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    //private lateinit var localDataStore:LocalUserManagerImpl
    //private val localDataStore by lazy { LocalUserManagerImpl(this) }
    @Inject
    lateinit var localDataStore: LocalUserManager
    private val onboardingViewModel: OnboardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        bottomNavigationView=binding.activityMainBottomNavigation
        //localDataStore=LocalUserManagerImpl(this)

        //navController=findNavController(binding.activityMainFragmentContainerView)
        navController=(supportFragmentManager.findFragmentById(binding.activityMainFragmentContainerView.id) as NavHostFragment).navController
        bottomNavigationView.setupWithNavController(navController)



        // Check onboarding flag asynchronously
        lifecycleScope.launch {
            val isOnboardingCompleted = localDataStore.readAppEntry()
            if (!isOnboardingCompleted) {
                navController.navigate(R.id.onboardingFragment)
            }
            val navGraph = navController.navInflater.inflate(R.navigation.nav_main)

            // Set correct start destination
            navGraph.setStartDestination(if (isOnboardingCompleted) R.id.homeFragment else R.id.onboardingFragment)
            navController.graph = navGraph
        }

        // Hide Bottom Nav in onboarding
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.onboardingFragment) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}