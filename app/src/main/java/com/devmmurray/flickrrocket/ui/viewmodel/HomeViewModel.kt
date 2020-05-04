package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.model.PhotoObject

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

//
//    private fun loadData() {
//        viewModelScope.launch {
//            try {
//                val result = Repository.searchPhotos()
//                Log.d("Load Data", "$result")
//
//                if (result.isSuccessful) {
//                    Log.d("LoadData", " ******* Load Data Successful ******")
//                    result.body()?.photos?.photoList?.forEach {
//                        Log.d("Home Fragment API", "${it.urlLink} **** ${it.title}")
//                        if (!it.urlLink.isNullOrEmpty() && !it.title.isNullOrEmpty()) {
//                            val link = it.urlLink
//                            val title = it.title
//                            val photo = link?.let { it1 -> PhotoObject(title, it1) }
//                            if (photo != null) {
//                                Log.d("Home Fragment API", "Each Photo: $photo")
//                                photoList.add(photo)
//                            }
//                        }
//                        Log.d("Home Fragment Api", "$photoList")
//                        _photos.value = photoList
//                    }
//                } else {
//                    Log.d("API", "Get Recent Photos Failed")
//                    _loadError.value = true
//                    _loading.value = false
//                    _photos.value = null
//                }
//            } catch (e: IOException) {
//                Log.d("API", "Api Failed IOException: ", e)
//                e.printStackTrace()
//                /**
//                 * Live Event for snackbar or alert dialog
//                 */
//
//            } catch (e: Exception) {
//                Log.d("API", "Api Failed Exception: ", e)
//            }
//        }
//    }
//
//    fun refresh() {
//        _loading.value = true
//        loadData()
//    }
//
//    fun nextPhoto() {
//        _loading.value = true
//        getPhotoPosition()
//    }
//
//    private fun getPhotoPosition() {
//        val size = photoList.size -1
//        if (position == size) {
//            position = 0
//        } else {
//            position++
//        }
//        _photoPosition.value = position
//    }
}