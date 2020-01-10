package thiago.flickr.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.ui.gallery.GalleryActivityViewModel

class GalleryViewModelTest {

    @Test
    fun testGetPhotoList() {
        val photoList = listOf(
            Photo("A", "A", "A", "A"),
            Photo("B", "B", "B", "B")
        )

        val expectedResults:LiveData<List<Photo>> = MutableLiveData<List<Photo>>(photoList)

        val fakeData = MutableLiveData<List<Photo>>(photoList)
        val viewModel = mock(GalleryActivityViewModel::class.java)
        Mockito.`when`(viewModel.getPhotoList()).thenReturn(fakeData)

        val result = viewModel.getPhotoList()

        assertEquals(result.value, expectedResults.value)
    }

    @Test
    fun testFetchPhotos() {
        val viewModel = mock(GalleryActivityViewModel::class.java)
        viewModel.fetchPhotos()
        verify(viewModel).fetchPhotos()
    }
}