package thiago.flickr.gallery.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import thiago.flickr.gallery.R
import thiago.flickr.gallery.data.model.Photo
import thiago.flickr.gallery.databinding.GalleryItemBinding

class GalleryItemAdapter(private var photos: List<Photo>, private val viewModel: GalleryActivityViewModel)
            : RecyclerView.Adapter<GalleryItemAdapter.GalleryItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemViewHolder {
        return GalleryItemViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.gallery_item, parent, false)
        )
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: GalleryItemViewHolder, position: Int) {
        val binding = holder.binding
        binding?.photo = photos[position]
        binding?.itemClickListener = viewModel
        binding?.executePendingBindings()
    }

    fun setPhotoList(photoList: List<Photo>) {
        photos = photoList
        notifyDataSetChanged()
    }

    class GalleryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding: GalleryItemBinding? = DataBindingUtil.bind(view)
    }
}