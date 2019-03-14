package bohdan.sushchak.elementzonetest.data.network.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("item")
    val item: String
)