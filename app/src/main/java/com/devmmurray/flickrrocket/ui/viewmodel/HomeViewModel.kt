package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private var position = 0

    private val _photoPosition by lazy { MutableLiveData<Int>() }
    val photoPosition: LiveData<Int>
        get() = _photoPosition


    fun nextPhoto(arraySize: Int) {
//        _loading.value = true
        getPhotoPosition(arraySize)
    }

    private fun getPhotoPosition(arraySize: Int) {
        if (position == arraySize) {
            position = 0
        } else {
            position++
        }
        _photoPosition.value = position
    }
}