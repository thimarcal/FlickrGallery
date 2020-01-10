package thiago.flickr.gallery.ui.gallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import thiago.flickr.gallery.Constants
import thiago.flickr.gallery.GalleryApplication
import thiago.flickr.gallery.R
import thiago.flickr.gallery.data.repository.Repository
import thiago.flickr.gallery.databinding.ActivityGalleryBinding
import thiago.flickr.gallery.ui.details.DetailsActivity
import javax.inject.Inject

class GalleryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryBinding
    @Inject lateinit var factory: GalleryActivityViewModelFactory
    @Inject lateinit var repository: Repository

    private val layoutManager by lazy {
        GridLayoutManager(this, COLUMNS)
    }
    private lateinit var adapter: GalleryItemAdapter

    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    private val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(GalleryActivityViewModel::class.java)
    }

    companion object {
        const val COLUMNS = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as GalleryApplication).component.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.layoutManager = layoutManager
        adapter = GalleryItemAdapter(emptyList(), viewModel)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = recyclerView.layoutManager?.itemCount
                if (itemCount == lastVisibleItemPosition + 1) {
                    viewModel.fetchPhotos()
                }
            }
        })

        viewModel.getPhotoList().observe(this, Observer {
            val photos = it ?: emptyList()
            adapter.setPhotoList(photos)

        })

        viewModel.clickEvent.observe(this, Observer {
            val photo = it.getIfPending()
            if (photo != null) {
                val intent = Intent(this, DetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_PHOTO, photo)
                startActivity(intent)
            }
        })

        viewModel.errorLoading.observe(this, Observer {
            val message = it.getIfPending()
            if (message != null) {
                val snackbar = Snackbar.make(binding.recyclerView, message, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction(R.string.dismiss, {
                    snackbar.dismiss()
                })
                snackbar.show()
            }
        })
    }
}
