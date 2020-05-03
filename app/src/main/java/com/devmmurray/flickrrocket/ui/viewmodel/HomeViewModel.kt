package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.data.repository.Repository
import kotlinx.coroutines.launch
import java.io.IOException

// result.body()?.photo?.photolist?

private const val BASE_URL =
    "https://api.flickr.com/services/rest/?format=json&sort=random&method=flickr.photos.search&tags=rocket&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var position = 0
    private val photoList = ArrayList<PhotoObject>()

    private val _photos by lazy { MutableLiveData<ArrayList<PhotoObject>>() }
    val photos: LiveData<ArrayList<PhotoObject>>
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


    fun refresh() {
        _loading.value = true
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val result = Repository.getRecentPhotosJson()
                Log.d("Load Data", "$result")

                if (result.isSuccessful) {
                    result.body()?.photos?.photoList?.forEach {
                        Log.d("API", "${it.urlLink} **** ${it.title}")
                        if (!it.urlLink.isNullOrEmpty() && !it.title.isNullOrEmpty()) {
                            val link = it.urlLink
                            val title = it.title
                            val photo = link?.let { it1 -> PhotoObject(title, it1) }
                            if (photo != null) {
                                photoList.add(photo)
                            }
                        }
                        _photos.value = photoList
                    }
                } else {
                    Log.d("API", "Get Recent Photos Failed")
                    _loadError.value = true
                    _loading.value = false
                    _photos.value = null
                }
            } catch (e: IOException) {
                Log.d("API", "Api Failed Exception: ", e)
                e.printStackTrace()
                /**
                 * Live Event for snackbar
                 */

            } catch (e: Exception) {
                Log.d("API", "Api Failed Exception: ", e)
            }
        }
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