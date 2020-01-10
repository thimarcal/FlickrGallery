package thiago.flickr.gallery.di.modules

import dagger.Module
import dagger.Provides
import thiago.flickr.gallery.ui.gallery.GalleryActivityViewModelFactory

@Module
class ViewModelModule {

    @Provides
    fun provideViewModelFactory() = GalleryActivityViewModelFactory()

}