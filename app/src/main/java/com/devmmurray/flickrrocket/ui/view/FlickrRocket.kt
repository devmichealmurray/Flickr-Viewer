package com.devmmurray.flickrrocket.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devmmurray.flickrrocket.R

class FlickrRocket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}
