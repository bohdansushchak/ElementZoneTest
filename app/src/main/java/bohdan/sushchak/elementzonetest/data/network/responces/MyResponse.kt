package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class MyResponse<out T: Any>(

    @SerializedName("data")
    val data: T?,

    @SerializedName("error")
    val error: ApiError?


)