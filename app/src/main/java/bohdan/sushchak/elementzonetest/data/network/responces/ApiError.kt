package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code")
    val code: Int,
    @SerializedName("exception")
    val exception: String,
    @SerializedName("line")
    val line: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("path")
    val path: String
)