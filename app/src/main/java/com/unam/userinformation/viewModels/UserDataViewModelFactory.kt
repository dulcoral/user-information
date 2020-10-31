package com.unam.userinformation.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider

class UserDataViewModelFactory(context: Context) :
    ViewModelProvider.Factory {
    private val mContext: Context = context
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDataViewModel(mContext) as T
    }
}