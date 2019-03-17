package bohdan.sushchak.elementzonetest.data.network.responces

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    @SerializedName("id")
    val id: Long,
    @SerializedName("item")
    val item: String,
    @SerializedName("order_id")
    val orderId: Long
): Parcelable