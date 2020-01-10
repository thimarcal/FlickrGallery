package thiago.flickr.gallery.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import retrofit2.HttpException
import thiago.flickr.gallery.data.db.PhotoDao
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.data.retrofit.FlickrApi
import javax.inject.Inject

class Repository @Inject constructor(private val photoDao: PhotoDao, private val flickrApi: FlickrApi) {

    private val onPhotoLoadListeners = ArrayList<OnPhotoLoadListener>()

    fun registerPhotoLoadListener(listener: OnPhotoLoadListener) {
        onPhotoLoadListeners.add(listener)
    }

    fun unRegisterPhotoLoadListener(listener: OnPhotoLoadListener) {
        onPhotoLoadListeners.remove(listener)
    }

    fun getPhotos(tags: String, page: Int = 1): LiveData<List<Photo>> {
        loadPhotos(tags, page)

        return photoDao.loadAll()
    }

    fun refresh(tags: String) {
        CoroutineScope(Dispatchers.IO).launch {
            photoDao.deleteAll()
        }
        loadPhotos(tags, 1)
    }

    fun loadPhotos(tags: String, page: Int = 1) {

        val handler = CoroutineExceptionHandler { _, _ ->
            onPhotoLoadListeners.forEach {
                CoroutineScope(Dispatchers.Main).launch {
                    it.onLoadFailed()
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch(handler) {
            val response = flickrApi.getPhotos(api_key, tags, page)

            try {
                if (response.isSuccessful) {
                    val photosResponse = response.body()
                    val photos = photosResponse?.photos?.photo

                    photos?.forEach {
                        if (it.largeSquareUrl != null && it.largeUrl != null) {
                            photoDao.savePhoto(it)
                        }
                    }

                    withContext(Dispatchers.Main) {
                        onPhotoLoadListeners.forEach {
                            it.onPhotosLoaded()
                        }
                    }

                } else {
                    onPhotoLoadListeners.forEach {
                        it.onLoadFailed()
                    }
                }
            } catch (e: HttpException) {
                onPhotoLoadListeners.forEach {
                    it.onLoadFailed()
                }
            } catch (e: Throwable) {
                onPhotoLoadListeners.forEach {
                    it.onLoadFailed()
                }
            }
        }

    }


    companion object {
        private lateinit var mInstance: Repository
        private lateinit var api_key: String
        fun getInstance(api_key: String, photoDao: PhotoDao, flickrApi: FlickrApi): Repository {
            this.api_key = api_key
            mInstance = Repository(photoDao, flickrApi)
            return mInstance
        }
    }

    interface OnPhotoLoadListener {
        fun onPhotosLoaded()
        fun onLoadFailed()
    }
}