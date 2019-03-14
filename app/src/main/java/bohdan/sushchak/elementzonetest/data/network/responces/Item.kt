package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("id")
    val id: Long,
    @SerializedName("item")
    val item: String,
    @SerializedName("order_id")
    val orderId: Long
)