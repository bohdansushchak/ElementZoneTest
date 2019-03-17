package bohdan.sushchak.elementzonetest.data.network.responces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    @SerializedName("date")
    val date: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("id_owner")
    val idOwner: Long,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("link")
    val link: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("price")
    val price: Float
) : Parcelable