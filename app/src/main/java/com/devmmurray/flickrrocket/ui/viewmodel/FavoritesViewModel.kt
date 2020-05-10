package com.devmmurray.flickrrocket.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {




    private val _loadError by lazy { MutableLiveData<Boolean>() }
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val _loading by lazy { MutableLiveData<Boolean>() }
    val loading: LiveData<Boolean>
        get() = _loading



}