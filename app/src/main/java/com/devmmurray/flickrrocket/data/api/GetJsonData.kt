package com.devmmurray.flickrrocket.data.api

import android.os.AsyncTask
import com.devmmurray.flickrrocket.data.model.PhotoObject
import org.json.JSONException
import org.json.JSONObject

class GetJsonData(private val listener: OnDataAvailable) :
    AsyncTask<String, Void, ArrayList<PhotoObject>>() {

    override fun doInBackground(vararg params: String): ArrayList<PhotoObject> {

        val jsonPhotos = ArrayList<PhotoObject>()

        try {
            val jsonString = JSONObject(getUrlAsString(params[0]))

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
                val photo = PhotoObject(link, title)
                jsonPhotos.add(photo)

            }

        } catch (e: JSONException) {
            e.printStackTrace()
            cancel(true)
        }

        return jsonPhotos
    }

    override fun onPostExecute(result: ArrayList<PhotoObject>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }
}