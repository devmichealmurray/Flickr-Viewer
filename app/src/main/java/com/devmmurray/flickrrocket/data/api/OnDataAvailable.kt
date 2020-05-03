package com.devmmurray.flickrrocket.data.api

import com.devmmurray.flickrrocket.data.model.PhotoObject

interface OnDataAvailable {
    fun onDataAvailable(data: ArrayList<PhotoObject>)
}