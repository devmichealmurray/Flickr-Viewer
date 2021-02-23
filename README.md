### Flickr-Viewer
One of my first personal projects showcasing what I had learned about Android app development with Kotlin. 
The app displays photos from Flickr and gives the user the ability to search, favorite (save), share, and put comments on the photos they save. The opening fragment is
inspired by TikTok, while I got the inspiration for the search screen from Instagram. 

## Tech
The app implements recyclerView for displaying full-size photos, thumbnails, and search suggestions. 
The architecture is MVVM utilizing LiveData and observables.
The app implements Navigation components and a custom BottomNavBar.
Room is used for data persistence, and Dagger2 for Dependency Injection.
Retrofit for networking with Flickr, Moshi for JSON parsing, and Picasso for image handling. 

