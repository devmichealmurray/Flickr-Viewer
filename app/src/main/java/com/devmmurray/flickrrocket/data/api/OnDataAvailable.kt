package com.devmmurray.flickrrocket.data.api

import com.devmmurray.flickrrocket.data.model.Photo

interface OnDataAvailable {
    fun onDataAvailable(data: ArrayList<Photo>)
}