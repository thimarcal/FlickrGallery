package thiago.flickr.gallery.data.retrofit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import thiago.flickr.gallery.data.model.PhotosResponse

interface FlickrApi {
    /*
    https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=f9cc014fa76b098f9e82f1c288379ea1&tags=kitten&page=1&format=json&nojsoncallback=1
     */
    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1&extras=url_l,url_q")
    suspend fun getPhotos(@Query("api_key") api_key:String,
                 @Query("tags") tags: String,
                 @Query("page") page: Int) : Response<PhotosResponse>


    companion object Factory{
        private const val BASE_URL = "https://api.flickr.com/"

        fun create() : Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
        }
    }
}