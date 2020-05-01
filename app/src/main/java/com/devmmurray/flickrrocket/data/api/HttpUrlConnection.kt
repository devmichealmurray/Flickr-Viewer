package com.devmmurray.flickrrocket.data.api

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@Throws(IOException::class)
internal fun getUrlAsString(url: String): String {
    val url = URL(url)
    val connection = url.openConnection() as HttpURLConnection
    connection.requestMethod = "GET"
    connection.setRequestProperty("Accept", "application/json")

    return try {
        val inputStream = connection.inputStream
        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            throw IOException("${connection.responseCode} for $url")
        }
        if (inputStream != null) {
            convertStreamToString(inputStream)
        } else {
            "Error Retrieving From $url"
        }
    } finally {
        connection.disconnect()
    }
}

@Throws(IOException::class)
private fun convertStreamToString(inputStream: InputStream): String {
    val reader = BufferedReader(InputStreamReader(inputStream))
    val sb = StringBuilder()
    var line = reader.readLine()
    while (line != null) {
        sb.append(line).append("\n")
        line = reader.readLine()
    }
    reader.close()
    return sb.toString()
}
