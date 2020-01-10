package thiago.flickr.gallery.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import thiago.flickr.gallery.data.model.Photo

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao():PhotoDao
}