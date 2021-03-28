package com.coooldoggy.itunesmusic.ui.common

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class BaseViewModel(application: Application): AndroidViewModel(application) {
    companion object{
        val TAG = BaseViewModel::class.java.simpleName
    }

    private val application = getApplication<Application>().applicationContext

    protected val sendToEvent = MutableLiveData<BaseViewModelEvent>()
    val viewModelEvent: LiveData<BaseViewModelEvent>
        get() = sendToEvent

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }
}