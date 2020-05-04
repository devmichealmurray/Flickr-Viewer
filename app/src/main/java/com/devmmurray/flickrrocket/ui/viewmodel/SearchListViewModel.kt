package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.model.PhotoObject

class SearchListViewModel(application: Application) : AndroidViewModel(application) {

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

//
//    fun refresh() {
//        _loading.value = true
//        loadData()
//    }

//    private fun loadData() {
//        viewModelScope.launch {
//            try {
//                val result = Repository.searchPhotos()
//
//                if (result.isSuccessful) {
//                    result.body()?.photos?.photoList?.forEach {
//                        if (!it.urlLink.isNullOrEmpty() && !it.title.isNullOrEmpty()) {
//                            val link = it.urlLink
//                            val title = it.title
//                            val photo = link?.let { it1 -> PhotoObject(title, it1) }
//                            if (photo != null) {
//                                photoList.add(photo)
//                            }
//                        }
//                        _photos.value = photoList
//                    }
//                } else {
//                    Log.d("API", "Get Recent Photos Failed")
//                    _loadError.value = true
//                    _loading.value = false
//                    _photos.value = null
//                }
//            } catch (e: IOException) {
//                Log.d("API", "Api Failed Exception: ", e)
//                e.printStackTrace()
//                /**
//                 * Live Event for snackbar
//                 */
//
//            } catch (e: Exception) {
//                Log.d("API", "Api Failed Exception: ", e)
//                e.printStackTrace()
//            }
//        }
//    }
//
}