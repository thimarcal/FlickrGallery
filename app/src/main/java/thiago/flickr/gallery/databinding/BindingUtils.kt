package thiago.flickr.gallery.databinding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.squareup.picasso.Picasso
import thiago.flickr.gallery.R

class BindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("imageSource")
        fun loadImage(imageView: ImageView, url: String) {
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.placeholder)
                    .into(imageView)
        }

        @JvmStatic
        @BindingAdapter("refreshing")
        fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, refreshing: Boolean) {
            swipeRefreshLayout.isRefreshing = refreshing
        }
    }
}