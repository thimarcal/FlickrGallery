package thiago.flickr.gallery.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GalleryActivityViewModelFactory  : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return GalleryActivityViewModel() as T
    }
}