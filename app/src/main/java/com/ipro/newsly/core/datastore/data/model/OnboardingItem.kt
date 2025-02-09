package com.ipro.newsly.core.datastore.data.model

import androidx.annotation.DrawableRes
import com.ipro.newsly.R

data class OnboardingItem(
    @DrawableRes val image: Int,
    val title: String,
    val description: String)

val onboardingItems = listOf(
    OnboardingItem(R.drawable.newsly_logo_with_text, "Welcome", "Discover new features."),
    OnboardingItem(R.drawable.newsly_logo_with_text, "Stay Organized", "Manage your tasks efficiently."),
    OnboardingItem(R.drawable.newsly_logo_with_text, "Get Started", "Start using the app now.")
)


