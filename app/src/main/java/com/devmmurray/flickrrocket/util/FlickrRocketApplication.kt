package com.devmmurray.flickrrocket.util

import android.app.Application

class FlickrRocketApplication: Application() {

    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }
}