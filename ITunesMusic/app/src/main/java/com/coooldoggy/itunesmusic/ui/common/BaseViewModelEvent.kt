package com.coooldoggy.itunesmusic.ui.common

import android.content.Context

sealed class BaseViewModelEvent {
    class Event(val eventId: Int, val param: Any): BaseViewModelEvent()

    class ShowToastMessage(val context: Context, val message: String): BaseViewModelEvent()

    class ShowProgressBar(val message: String? = null): BaseViewModelEvent()

    class HideProgressBar(val message: String? = null): BaseViewModelEvent()
}