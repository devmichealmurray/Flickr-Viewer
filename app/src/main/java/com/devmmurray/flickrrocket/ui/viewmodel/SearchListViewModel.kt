package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.devmmurray.flickrrocket.data.api.FlickrApiService
import com.devmmurray.flickrrocket.data.api.GetJsonData
import com.devmmurray.flickrrocket.data.api.OnDataAvailable
import com.devmmurray.flickrrocket.data.model.Photo
import io.reactivex.disposables.CompositeDisposable

private const val BASE_URL =
    "https://api.flickr.com/services/rest/?format=json&sort=random&method=flickr.photos.search&tags=rocket&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."
class SearchListViewModel(application: Application) : AndroidViewModel(application), OnDataAvailable {

    /**
     *  Adjust with _liveData so that MutableLiveData is not exposed
     */

    val photos by lazy { MutableLiveData<ArrayList<Photo>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = FlickrApiService()

    fun refresh() {
        Log.d("SearchListViewModel", "Refresh Called")
        loading.value = true
        val getJsonData = GetJsonData(this)
        getJsonData.execute(BASE_URL)
        loading.value = false
    }

    override fun onDataAvailable(data: ArrayList<Photo>) {
        Log.d("SearchListViewModel", "Response Returned $data")
        photos.value = data
    }

    //    private fun getPhotoUrlData() {
//        disposable.add(
//            apiService.getOpenSearchJson()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object : DisposableSingleObserver<Response>() {
//                    override fun onSuccess(t: Response) {
//                        Log.d(".getPhotoUrlData", "on Success returns $t")
//                        loadError.value = false
//                        loading.value = false
//                        val list = t
//                    }
//
//                    override fun onError(e: Throwable) {
//                        Log.e(".getPhotoUrlData", "Error ${e.localizedMessage}")
//                        e.printStackTrace()
//                        loadError.value = true
//                        loading.value = false
//                        photos.value = null
//                    }
//                })
//        )
//    }



}