package com.ipro.newsly.feature.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ipro.newsly.core.datastore.domain.manager.LocalUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val localUserManager: LocalUserManager
): ViewModel() {

    fun readOnBoardingState():Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
    /*
    suspend fun readOnBoardingState():Boolean {
        return localUserManager.readAppEntry()
    }*/




    fun saveOnBoardingState() {
        viewModelScope.launch(Dispatchers.IO){
            localUserManager.saveAppEntry()
        }
    }



}