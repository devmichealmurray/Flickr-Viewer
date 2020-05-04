package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.devmmurray.flickrrocket.data.model.PhotoObject
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.FLICKR_QUERY
import com.devmmurray.flickrrocket.data.repository.Repository
import kotlinx.coroutines.launch
import java.io.IOException

class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val sharedPref: SharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(application)

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
        val queryResults = sharedPref.getString(FLICKR_QUERY, "")
        val query = queryResults
    Log.d("Refresh **********", "******************************** $queryResults")
        if (query != null && query != "") {
            loadData(query)
        } else {
            loadData("rocket")
        }
    }

    private fun loadData(query: String) {
        viewModelScope.launch {
            try {
                val result = Repository.searchPhotos(query)
                if (result.isSuccessful) {
                    result.body()?.photos?.photoList?.forEach {
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
                    _loadError.value = true
                    _loading.value = false
                    _photos.value = null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                /**
                 * Live Event for snackbar or alert dialog
                 */

            } catch (e: Exception) {
            }
        }
    }

    fun nextPhoto() {
        _loading.value = true
        getPhotoPosition()
    }

    private fun getPhotoPosition() {
        val size = photoList.size -1
        if (position == size) {
            position = 0
        } else {
            position++
        }
        _photoPosition.value = position
    }
}
