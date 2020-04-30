package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.api.FlickrApiService
import com.devmmurray.flickrrocket.data.model.Photo
import com.devmmurray.flickrrocket.data.model.UrlData
import io.reactivex.disposables.CompositeDisposable

private const val BASE_URL =
    "https://api.flickr.com/services/rest/?format=json&method=flickr.photos.getRecent&api_key=0e2b6aaf8a6901c264acb91f151a3350"

class SearchListViewModel(application: Application) : AndroidViewModel(application) {

    /**
     *  Adjust with _liveData so that MutableLiveData is not exposed
     */

    val photos by lazy { MutableLiveData<ArrayList<Photo>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = FlickrApiService()

    fun refresh() {
        loading.value = true
        getPhotoUrlData()
    }

    private fun getPhotoUrlData() {
        val flickrService = FlickrApiService()
        val response = flickrService.getOpenSearchJson()
        if (response != null) {
            buildUri(response)
        }
    }

    private fun buildUri(list: ArrayList<UrlData>) {
        val photoList = ArrayList<Photo>()

        for (i in 0 until list.size) {
            val item = list[i]
            val farm = item.farm
            val server = item.server
            val id = item.id
            val secret = item.secret
            val title = item.title
            val link = "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}_m.jpg"
            val photo = Photo(link, title)
            photoList.add(photo)
        }
        photos.value = photoList
    }

}