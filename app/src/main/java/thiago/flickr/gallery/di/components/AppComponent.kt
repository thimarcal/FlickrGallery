package thiago.flickr.gallery.di.components

import android.app.Application
import dagger.Component
import thiago.flickr.gallery.di.modules.AppModule
import thiago.flickr.gallery.di.modules.DataModule
import thiago.flickr.gallery.di.modules.ViewModelModule
import thiago.flickr.gallery.ui.gallery.GalleryActivity
import thiago.flickr.gallery.ui.gallery.GalleryActivityViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(app: Application)

    fun inject(activity: GalleryActivity)

    fun inject(viewModel: GalleryActivityViewModel)
}