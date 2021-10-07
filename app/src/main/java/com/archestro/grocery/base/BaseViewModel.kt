package com.archestro.grocery.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.archestro.grocery.domain.usecases.base.Outcome
import kotlinx.coroutines.cancel

abstract class BaseViewModel: ViewModel() {
    val scope = viewModelScope
    var outcomeLiveData = MediatorLiveData<Outcome<*>>()

    // Cancel the job when the view model is destroyed
    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}