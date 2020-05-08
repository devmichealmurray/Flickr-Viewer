package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private var position = 0

    private val _photoPosition by lazy { MutableLiveData<Int>() }
    val photoPosition: LiveData<Int>
        get() = _photoPosition


    fun nextPhoto() {
//        _loading.value = true
        getPhotoPosition()
    }

    private fun getPhotoPosition() {
        val arraySize = photos.value?.size
        if (position == arraySize) {
            position = 0
        } else {
            position++
        }
        _photoPosition.value = position
    }

    fun positionUpdate(currentPosition: Int) {
        updatePhotoPosition(currentPosition)
    }

    private fun updatePhotoPosition(currentPosition: Int) {
        val arraySize = photos.value?.size
        if (position == arraySize) {
            position = 0
        } else {
            position = currentPosition
        }
    }
}