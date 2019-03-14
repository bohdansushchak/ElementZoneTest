package bohdan.sushchak.elementzonetest.data.network.model

import bohdan.sushchak.elementzonetest.data.network.responces.Item
import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("api_token")
    val apiToken: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("location")
    val location: String,

    @SerializedName("price")
    val price: Float,

    @SerializedName("items")
    val items: List<Item>
)