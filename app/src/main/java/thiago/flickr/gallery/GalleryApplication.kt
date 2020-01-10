package thiago.flickr.gallery

import android.app.Application
import thiago.flickr.gallery.di.components.AppComponent
import thiago.flickr.gallery.di.components.DaggerAppComponent
import thiago.flickr.gallery.di.modules.DataModule


class GalleryApplication: Application() {

    init {
        instance = this
    }

    val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .dataModule(DataModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

    companion object {
        lateinit var instance: GalleryApplication
            private set
    }
}