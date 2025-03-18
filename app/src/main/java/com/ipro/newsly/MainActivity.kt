package com.ipro.newsly

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ipro.newsly.databinding.ActivityMainBinding
import com.ipro.newsly.core.datastore.data.manager.LocalUserManagerImpl
import com.ipro.newsly.core.datastore.domain.manager.LocalUserManager
import com.ipro.newsly.feature.onboarding.presentation.OnboardingActivity
import com.ipro.newsly.feature.onboarding.presentation.OnboardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolBar:Toolbar
    //private lateinit var localDataStore:LocalUserManagerImpl
    //private val localDataStore by lazy { LocalUserManagerImpl(this) }
    //@Inject
    //lateinit var localDataStore: LocalUserManager
    private val onboardingViewModel: OnboardingViewModel by viewModels<OnboardingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isDataLoaded = true
        installSplashScreen().apply {
            setKeepOnScreenCondition{isDataLoaded}
        }
        //Log.d("readOnBoarding", "EnteredMain")


        // Check onboarding flag asynchronously
        lifecycleScope.launch{
            // Ensure it's running on IO
            val isOnboardingCompleted = withContext(Dispatchers.IO) {onboardingViewModel.readOnBoardingState().first()}
            //val isOnboardingCompleted = onboardingViewModel.readOnBoardingState().first()
            //Log.d("readOnBoarding", it.toString())

            withContext(Dispatchers.Main) {
                if (!isOnboardingCompleted) {
                    startActivity(Intent(this@MainActivity, OnboardingActivity::class.java))
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                        overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
                    } else {
                        overridePendingTransition(0, 0)
                    }
                    finish()

                } else {
                    // Only set content view if onboarding is completed
                    setupUI()
                }
                isDataLoaded = false
            }
        }

        //setupUI()

    }


    private fun setupUI() {
        binding=ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)
        /*ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/



        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment,R.id.favoriteFragment,R.id.searchFragment,R.id.settingsFragment)
        )


        toolBar=binding.activityMainToolbar
        //setSupportActionBar(toolBar)
        bottomNavigationView=binding.activityMainBottomNavigation
        //localDataStore=LocalUserManagerImpl(this)

        //navController=findNavController(binding.activityMainFragmentContainerView)
        navController=(supportFragmentManager.findFragmentById(binding.activityMainFragmentContainerView.id) as NavHostFragment).navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        /*
                // Set correct start destination
                navGraph.setStartDestination(if (isOnboardingCompleted) R.id.homeFragment else R.id.onboardingFragment)
                navController.graph = navGraph
                */
        bottomNavigationView.setupWithNavController(navController)
        toolBar.setupWithNavController(navController, appBarConfiguration)


        /*
                // Check onboarding flag asynchronously
                lifecycleScope.launch {
                    val isOnboardingCompleted = onboardingViewModel.readOnBoardingState()
                    if (!isOnboardingCompleted) {
                        navController.navigate(R.id.onboardingFragment)
                    }
                    val navGraph = navController.navInflater.inflate(R.navigation.nav_main)

                    // Set correct start destination
                    navGraph.setStartDestination(if (isOnboardingCompleted) R.id.homeFragment else R.id.onboardingFragment)
                    navController.graph = navGraph
                }*/

        /*
                // Hide Bottom Nav in onboarding
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    if (destination.id == R.id.onboardingFragment) {
                        bottomNavigationView.visibility = View.GONE
                    } else {
                        bottomNavigationView.visibility = View.VISIBLE
                    }
                }
                */
    }
}