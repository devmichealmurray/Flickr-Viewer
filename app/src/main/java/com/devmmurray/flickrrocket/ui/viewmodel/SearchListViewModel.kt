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

class SearchListViewModel(application: Application) : AndroidViewModel(application), OnDataAvailable {

    private val _photos by lazy { MutableLiveData<ArrayList<Photo>>() }
    val photos: LiveData<ArrayList<Photo>>
        get() = _photos

    private val _loadError by lazy { MutableLiveData<Boolean>() }
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean>
        get() = _loading


    fun refresh() {
        Log.d("SearchListViewModel", "Refresh Called")
        _loading.value = true
        /**
         * this will be the function call when sharedprefs is setup
         */
//        runNewApiCall("rocket")

        /**
         * Just a stub function until search is completed
         */
        val getJsonData = GetJsonData(this)
        getJsonData.execute(BASE_URL)
    }

    fun onSuggestionClick(search: String) {
        _loading.value = true
        runNewApiCall(search)
    }

    private fun runNewApiCall(search: String) {
        val getJsonData = GetJsonData(this)
        getJsonData.execute(BASE_URL)
    }

    override fun onDataAvailable(data: ArrayList<Photo>) {
        Log.d("SearchListViewModel", "Response Returned $data")
        _photos.value = data
        _loading.value = false
    }



//        private val disposable = CompositeDisposable()
//    private val apiService = FlickrApiService()

//        private fun getPhotoUrlData() {
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