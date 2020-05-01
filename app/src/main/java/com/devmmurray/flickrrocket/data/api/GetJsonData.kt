package com.devmmurray.flickrrocket.data.api

import android.os.AsyncTask
import android.util.Log
import com.devmmurray.flickrrocket.data.model.Photo
import org.json.JSONException
import org.json.JSONObject

class GetJsonData(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<Photo>>() {

    override fun doInBackground(vararg params: String): ArrayList<Photo> {
        Log.d("GetJsonData", "DoInBackground Called")
        val jsonPhotos = ArrayList<Photo>()

        try {
            val jsonString = JSONObject(getUrlAsString(params[0]))
            Log.d("GetJsonData", "$jsonString")
            val jsonPhotosObj = jsonString.getJSONObject("photos")
            val photosArray = jsonPhotosObj.getJSONArray("photo")

            for (i in 0 until photosArray.length()) {
                val photoObj = photosArray.getJSONObject(i)
                val farm = photoObj.getString("farm")
                val server = photoObj.getString("server")
                val id = photoObj.getString("id")
                val secret = photoObj.getString("secret")
                val title = photoObj.getString("title")

                val link = "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}_m.jpg"
                val photo = Photo(link, title)
                Log.d("DoInBackground", "${photo.link}")
                jsonPhotos.add(photo)

            }

        } catch (e: JSONException) {
            e.printStackTrace()
            cancel(true)
        }
        Log.d("DoInBackground", " Returning $jsonPhotos")
        return jsonPhotos
    }

    override fun onPostExecute(result: ArrayList<Photo>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }
}