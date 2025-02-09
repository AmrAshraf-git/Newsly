package com.ipro.newsly.feature.onboarding.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ipro.newsly.R
import com.ipro.newsly.databinding.FragmentOnboardingBinding
import com.ipro.newsly.core.datastore.data.manager.LocalUserManagerImpl
import com.ipro.newsly.core.datastore.data.model.OnboardingItem
import com.ipro.newsly.core.datastore.data.model.onboardingItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var _binding: FragmentOnboardingBinding? = null
    private val binding get() = _binding!!

    private val onboardingViewModel: OnboardingViewModel by viewModels()

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonNext: Button
    private lateinit var tabLayout: TabLayout
    //private lateinit var localDataStore: LocalUserManagerImpl
    //private val localDataStore by lazy { LocalUserManagerImpl(requireContext()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOnboardingBinding.inflate(inflater,container,false)

        viewPager = binding.fragmentOnboardingViewPager
        buttonNext = binding.fragmentOnboardingButtonNext
        tabLayout=binding.fragmentOnboardingTabLayout
        //localDataStore=LocalUserManagerImpl(requireContext())



        viewPager.adapter = OnboardingAdapter(onboardingItems)

        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()

        buttonNext.setOnClickListener {
            if (viewPager.currentItem + 1 < onboardingItems.size) {
                viewPager.currentItem += 1
            } else {
                //saveOnboardingCompleted()
                //lifecycleScope.launch {
                    //localDataStore.saveAppEntry()
                    onboardingViewModel.saveOnBoardingState()
                    findNavController().navigate(R.id.action_onboardingFragment_to_homeFragment)
                //}
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}