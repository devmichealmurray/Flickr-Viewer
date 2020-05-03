package com.devmmurray.flickrrocket.data.model

class UrlAddress {

    companion object {
        const val OPEN_SEARCH =
            "https://api.flickr.com/services/rest/?format=json&method=flickr.photos.getRecent&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."

        const val BASE_URL = "https://api.flickr.com/services/"

        const val API_KEY = "0e2b6aaf8a6901c264acb91f151a3350"
        const val SEARCH_METHOD = "flickr.photos.search"
        const val SORT = "random"
        const val TAG_MODE = "all"
        const val OPEN_SEARCH_METHOD = "flickr.photos.getRecent"

        const val FULL_ROCKET_URL =
            "https://api.flickr.com/services/rest/?format=json&sort=random&method=flickr.photos.search&tags=rocket&tag_mode=all&api_key=0e2b6aaf8a6901c264acb91f151a3350&nojsoncallback=1."
    }
}
//   Search String Params
//  format=json
//   method=flickr.photos.search
//   tags=$searchTerms
//   tag_mode=all
//   api_key=0e2b6aaf8a6901c264acb91f151a3350
//   nojsoncallback=1
