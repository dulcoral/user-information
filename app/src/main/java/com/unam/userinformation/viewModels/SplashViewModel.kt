package com.unam.userinformation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    val liveData: LiveData<SplashState>
        get() = mutableLiveData
    private val mutableLiveData = MutableLiveData<SplashState>()

    init {
        GlobalScope.launch {
            // Me tome la libertad de colocar 2 seg, debido a que el tiempo de espera era demasido corto para leer la info
            delay(3000)
            mutableLiveData.postValue(SplashState.SplashActivity)
        }
    }
}

sealed class SplashState {
    object SplashActivity : SplashState()
}