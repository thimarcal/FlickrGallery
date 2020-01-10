# Challenge

This app was developed by Thiago Gomes Marçal Pereira as a challenge for an Android position

## Flickr Gallery

The app intends to load images from FlickrApi, and display them in a GridLayout with two columns.

As part of the requirements, it should just load images filtering for kitten images.

## Architecture

Application was developed using MVVM architecture, for a better separation of concerns
Also used:

- Dagger2 for Dependency Injection
- Coroutines for Network and Disk (DB) access
- Junit/Mockito for implementing a few tests
- RecyclerView with infinite scroll
- Databinding
- Room for offline persistence

