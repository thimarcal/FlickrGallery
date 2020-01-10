package thiago.flickr.gallery

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import thiago.flickr.gallery.data.db.PhotoDao
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.data.repository.Repository
import thiago.flickr.gallery.data.retrofit.FlickrApi

class RepositoryTest {

    class FakeDao(private val list: LiveData<List<Photo>>) : PhotoDao {
        private var photos = mutableListOf<Photo>()
        override fun savePhoto(photo: Photo) {
            photos.add(photo)
        }

        override fun deleteAll() {
            photos.clear()
        }

        override fun loadAll(): LiveData<List<Photo>> {
            photos.clear()
            photos.addAll(list.value!!)
            return MutableLiveData(photos)
        }
    }

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Test
    fun testGetPhotosSuccess() {
        val expectedResults = listOf(
            Photo("A", "A", "A", "A"),
            Photo("B", "B", "B", "B")
        )
        val fakeData = MutableLiveData<List<Photo>>()
        fakeData.value = expectedResults


        val fakeDao = FakeDao(fakeData)
        val fakeApi = mock(FlickrApi::class.java)

        val repository = Repository.getInstance("...", fakeDao, fakeApi)

        repository.getPhotos("fake_tag", 1).observeForever { returnedPhotos -> assertEquals(expectedResults, returnedPhotos) }
    }
}