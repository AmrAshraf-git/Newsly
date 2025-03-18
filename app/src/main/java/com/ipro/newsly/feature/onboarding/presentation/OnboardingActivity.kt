package com.ipro.newsly.feature.onboarding.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ipro.newsly.MainActivity
import com.ipro.newsly.R
import com.ipro.newsly.core.datastore.data.model.onboardingItems
import com.ipro.newsly.databinding.ActivityMainBinding
import com.ipro.newsly.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnboardingBinding
    private val onboardingViewModel: OnboardingViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonNext: Button
    private lateinit var tabLayout: TabLayout
    //private lateinit var localDataStore: LocalUserManagerImpl
    //private val localDataStore by lazy { LocalUserManagerImpl(requireContext()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityOnboardingBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = binding.activityOnboardingViewPager
        buttonNext = binding.activityOnboardingButtonNext
        tabLayout=binding.activityOnboardingTabLayout
        //localDataStore=LocalUserManagerImpl(requireContext())

        viewPager.adapter = OnboardingAdapter(onboardingItems)
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        buttonNext.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingItems.size) {
                viewPager.currentItem += 1
            } else {
                completeOnboarding()
            }
        }


    }

    private fun completeOnboarding() {
        //saveOnboardingCompleted()
        //lifecycleScope.launch {
        //localDataStore.saveAppEntry()

        lifecycleScope.launch {
            onboardingViewModel.saveOnBoardingState() // Runs in Dispatchers.IO in ViewModel

            // Ensure the write operation completes before switching to MainActivity
            delay(100) // Small delay to avoid race conditions

            // Switch to Main thread before starting MainActivity
            withContext(Dispatchers.Main) {

                val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                startActivity(intent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, 0, 0)
                } else {
                    overridePendingTransition(0, 0)
                }
                finish()
            }
        }

    }
}