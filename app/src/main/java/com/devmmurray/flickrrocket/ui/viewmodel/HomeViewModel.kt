package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.database.UseCases
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.data.usecase.AddFavorite
import com.devmmurray.flickrrocket.data.usecase.GetAllFavorites
import com.devmmurray.flickrrocket.data.usecase.GetFavorite
import com.devmmurray.flickrrocket.data.usecase.RemoveFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    private var position = 0
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val useCases = UseCases(
        AddFavorite(repository),
        GetAllFavorites(repository),
        GetFavorite(repository),
        RemoveFavorite(repository)
    )

    private val _saved by lazy { MutableLiveData<Boolean>() }
    val saved: LiveData<Boolean>
        get() = _saved

    private val _removed by lazy { MutableLiveData<Boolean>() }
    val removed: LiveData<Boolean>
        get() = _removed

    private val _photoPosition by lazy { MutableLiveData<Int>() }
    val photoPosition: LiveData<Int>
        get() = _photoPosition


    fun nextPhoto() {
//        _loading.value = true
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
        Log.d("Save Favorite", "******* Save Favorite Called ${photo.title} ********")
        coroutineScope.launch {
            useCases.addFavorite(photo)
        }
        _saved.value = true
    }

    fun remove(photo: PhotoObject) {
        removeFavorite(photo)
    }

    private fun removeFavorite(photo: PhotoObject) {
        Log.d("Remove Favorite", "******* Remove Favorite Called ${photo.title} ********")
        coroutineScope.launch {
            useCases.removeFavorite(photo)
        }
        _removed.value = true
    }

    fun share(url: String) {
        textShare(url)
    }

    private fun textShare(url: String) {

    }
}