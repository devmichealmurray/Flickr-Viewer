package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.preference.PreferenceManager
import com.devmmurray.flickrrocket.data.database.RoomFavoritesDataSource
import com.devmmurray.flickrrocket.data.database.UseCases
import com.devmmurray.flickrrocket.data.model.UrlAddress.Companion.FLICKR_QUERY
import com.devmmurray.flickrrocket.data.model.domain.PhotoObject
import com.devmmurray.flickrrocket.data.repository.ApiRepository
import com.devmmurray.flickrrocket.data.repository.DbRepository
import com.devmmurray.flickrrocket.data.usecase.AddFavorite
import com.devmmurray.flickrrocket.data.usecase.GetAllFavorites
import com.devmmurray.flickrrocket.data.usecase.GetFavorite
import com.devmmurray.flickrrocket.data.usecase.RemoveFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = DbRepository(RoomFavoritesDataSource(application))
    private val useCases = UseCases(
        AddFavorite(repository),
        GetAllFavorites(repository),
        GetFavorite(repository),
        RemoveFavorite(repository)
    )

    private val sharedPref: SharedPreferences = PreferenceManager
        .getDefaultSharedPreferences(application)

    private val _photos by lazy { MutableLiveData<ArrayList<PhotoObject>>() }
    val photos: LiveData<ArrayList<PhotoObject>>
        get() = _photos

    private val _loadError by lazy { MutableLiveData<Boolean>() }
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean>
        get() = _loading

    fun refresh(isFavorites: Boolean) {
        _loading.value = true
        val queryResults = sharedPref.getString(FLICKR_QUERY, "")
        if (isFavorites) {
            loadDbData()
        } else {
            if (queryResults != null && queryResults != "") {
                loadApiData(queryResults)
            } else {
                loadApiData("rocket")
            }
        }
    }

    private fun loadApiData(query: String) {
        val photoList = ArrayList<PhotoObject>()
        viewModelScope.launch {
            try {
                val result = ApiRepository.searchPhotos(query)
                if (result.isSuccessful) {
                    result.body()?.photos?.photoList?.forEach {
                        if (!it.urlLink.isNullOrEmpty() && !it.title.isNullOrEmpty()) {
                            val link = it.urlLink
                            val title = it.title
                            val photo = link?.let { it1 ->
                                PhotoObject(
                                    title = title,
                                    link = it1
                                )
                            }
                            if (photo != null) {
                                photoList.add(photo)
                            }
                        }
                        _loading.value = false
                        _loadError.value = false
                        _photos.value = photoList
                    }
                } else {
                    _loadError.value = true
                    _loading.value = false
                }
            } catch (e: IOException) {
                e.printStackTrace()
                /**
                 * Live Event for snackbar or alert dialog
                 */

            } catch (e: Exception) {
                /**
                 * Live Event for snackbar or alert dialog
                 */
            }
        }
    }

    private fun loadDbData() {
        Log.d("Favorites View Model", "************ Load Data Called **************")
        coroutineScope.launch {
            val list = useCases.getAllFavorites()
            _photos.postValue(list as ArrayList<PhotoObject>?)
        }
        _loading.value = false
        _loadError.value = false
    }

    override fun onCleared() {
        super.onCleared()
        sharedPref.edit().putString(FLICKR_QUERY, "rocket").apply()
    }

}
