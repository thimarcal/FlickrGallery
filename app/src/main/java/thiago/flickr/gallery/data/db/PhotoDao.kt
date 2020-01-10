package thiago.flickr.gallery.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import thiago.flickr.gallery.data.model.Photo

@Dao
interface PhotoDao {

    @Insert(onConflict = REPLACE)
    suspend fun savePhoto(photo: Photo)

    @Query("DELETE from photo")
    fun deleteAll()

    @Query("SELECT * FROM photo")
    fun loadAll() : LiveData<List<Photo>>
}