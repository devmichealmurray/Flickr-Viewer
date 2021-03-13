### Flickr-Viewer
One of my first personal projects developed in May of 2020, showcasing what I had learned in the first five months studying Android development with Kotlin.
The app displays photos from Flickr and gives the user the ability to search, favorite (save), share, and put comments on the photos they save. The opening fragment is
inspired by TikTok, while I got the inspiration for the search screen from Instagram. 

## Tech
The app implements recyclerView for displaying full-size photos, thumbnails, and search suggestions. 
The architecture is MVVM utilizing LiveData and observables.
The app implements Navigation components and a custom BottomNavBar.
Room is used for data persistence, and Dagger2 for Dependency Injection.
Retrofit for networking with Flickr, Moshi for JSON parsing, and Picasso for image handling. 

<img src="https://github.com/devmichealmurray/Flickr-Viewer/blob/master/Flicker_View_01%5B1%5D.jpg" alt="Home Screen" width="200" height="350"> <img src ="https://github.com/devmichealmurray/Flickr-Viewer/blob/master/Flicker_View_02%5B1%5D.jpg" alt="Comments Dialog" width="200" height="350"> <img src ="https://github.com/devmichealmurray/Flickr-Viewer/blob/master/Flicker_View_03%5B1%5D.jpg" alt="Saved Favorites Fragment" width="200" height="350"> <img src ="https://github.com/devmichealmurray/Flickr-Viewer/blob/master/Flicker_View_04%5B1%5D.jpg" alt="Search Fragment" width="200" height="350">
