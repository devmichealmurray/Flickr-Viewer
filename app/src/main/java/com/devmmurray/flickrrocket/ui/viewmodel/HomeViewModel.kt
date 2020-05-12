package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private var position = 0
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _saved by lazy { MutableLiveData<Boolean>() }
    val saved: LiveData<Boolean>
        get() = _saved

    private val _photoPosition by lazy { MutableLiveData<Int>() }
    val photoPosition: LiveData<Int>
        get() = _photoPosition


    fun nextPhoto() {
        getPhotoPosition()
    }

    private fun getPhotoPosition() {
        val arraySize = photos.value?.size?.minus(1)
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
        val arraySize = photos.value?.size?.minus(1)
        position = if (position == arraySize) 0 else currentPosition
    }

    fun save(photo: PhotoObject) {
        saveFavorite(photo)
    }

    private fun saveFavorite(photo: PhotoObject) {
        coroutineScope.launch {
            useCases.addFavorite(photo)
        }
        _saved.value = true
    }

    fun remove(photo: PhotoObject) {
        removeFavorite(photo)
    }

    private fun removeFavorite(photo: PhotoObject) {
        coroutineScope.launch {
            useCases.removeFavorite(photo)
        }
        _saved.value = false
    }

}