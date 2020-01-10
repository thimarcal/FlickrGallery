package thiago.flickr.gallery.ui.gallery

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thiago.flickr.gallery.Constants
import thiago.flickr.gallery.GalleryApplication
import thiago.flickr.gallery.R
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.data.repository.Repository
import thiago.flickr.gallery.event.Event
import javax.inject.Inject

class GalleryActivityViewModel: ViewModel(), Repository.OnPhotoLoadListener {

    private var currentPage: Int = 1

    @Inject
    lateinit var repository:Repository

    var isLoading : MutableLiveData<Boolean> = MutableLiveData()
    var errorLoading : MutableLiveData<Event<String>> = MutableLiveData()

    var clickEvent: MutableLiveData<Event<Photo>> = MutableLiveData()

    init {
        GalleryApplication.instance.component.inject(this)
        repository.registerPhotoLoadListener(this)
        isLoading.value = true
        repository.loadPhotos(Constants.PHOTO_TAG)
    }

    fun getPhotoList() = repository.getPhotos(Constants.PHOTO_TAG, currentPage)

    fun loadAll() {
        currentPage = 1
        isLoading.value = true
        repository.refresh(Constants.PHOTO_TAG)
    }

    fun fetchPhotos() {
        currentPage++
        isLoading.value = true
        repository.loadPhotos(Constants.PHOTO_TAG, currentPage)
    }

    override fun onCleared() {
        super.onCleared()
        repository.unRegisterPhotoLoadListener(this)
    }

    override fun onPhotosLoaded() {
        isLoading.value = false
    }

    override fun onLoadFailed() {
        isLoading.value = false
        errorLoading.value = Event(GalleryApplication.instance.getString(R.string.no_data))
    }

    fun onItemClicked(photo: Photo) {
        clickEvent.value = Event(photo)
    }
}