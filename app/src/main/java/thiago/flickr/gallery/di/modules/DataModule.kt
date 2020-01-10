package thiago.flickr.gallery.di.modules

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import thiago.flickr.gallery.BuildConfig
import thiago.flickr.gallery.data.db.PhotoDao
import thiago.flickr.gallery.data.db.PhotoDatabase
import thiago.flickr.gallery.data.repository.Repository
import thiago.flickr.gallery.data.retrofit.FlickrApi

@Module
class DataModule(private val mApplication: Application) {
    private var mDatabase: PhotoDatabase =
        Room.databaseBuilder(mApplication, PhotoDatabase::class.java, "photo_db").build()

    @Provides
    fun provideRoomDb() = mDatabase

    @Provides
    fun providePhotoDao(database: PhotoDatabase) = database.photoDao()

    @Provides
    fun provideRepository(photoDao: PhotoDao, flickrApi: FlickrApi) =
        Repository.getInstance(BuildConfig.FLICKR_API_KEY, photoDao, flickrApi)

    @Provides
    fun provideRetrofit() = FlickrApi.create()

    @Provides
    fun provideFlickrService(retrofit: Retrofit): FlickrApi = retrofit.create(FlickrApi::class.java)
}