package thiago.flickr.gallery.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Photo(
    @PrimaryKey @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("url_q") val largeSquareUrl: String,
    @SerializedName("url_l") val largeUrl: String
) : Serializable