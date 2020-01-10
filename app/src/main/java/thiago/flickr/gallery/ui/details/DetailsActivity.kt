package thiago.flickr.gallery.ui.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import thiago.flickr.gallery.Constants
import thiago.flickr.gallery.R
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        if (intent.hasExtra(Constants.EXTRA_PHOTO)) {
            val photo:Photo = intent.getSerializableExtra(Constants.EXTRA_PHOTO) as Photo
            binding.photo = photo
            this.title = photo.title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
