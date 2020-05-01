package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.api.GetJsonData
import com.devmmurray.flickrrocket.data.api.OnDataAvailable
import com.devmmurray.flickrrocket.data.model.Photo

private const val BASE_URL =
    "https://api.flickr.com/services/rest/?format=json&sort=random&method=flickr.photos.search&tags=rocket&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."


class HomeViewModel(application: Application): AndroidViewModel(application), OnDataAvailable {

    private val _photos by lazy { MutableLiveData<ArrayList<Photo>>() }
    val photos: LiveData<ArrayList<Photo>>
        get() = _photos

    private val _loadError by lazy { MutableLiveData<Boolean>() }
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean>
        get() = _loading

    private val _photoPosition by lazy { MutableLiveData<Int>() }
    val photoPosition: LiveData<Int>
        get() = _photoPosition

    private var position = 0

    fun refresh() {
        Log.d("HomeViewModel", "Refresh Called")
        _loading.value = true
        val getJsonData = GetJsonData(this)
        getJsonData.execute(BASE_URL)
    }

    override fun onDataAvailable(data: ArrayList<Photo>) {
        Log.d("HomeViewModel", "Response Returned $data")
        _photos.value = data
        _loading.value = false
    }

    fun nextPhoto() {
        _loading.value = true
        getPhotoPosition()
    }

    private fun getPhotoPosition() {
        if (position != 99) {
            position++
        } else {
            position = 0
        }
        _photoPosition.value = position
    }
}