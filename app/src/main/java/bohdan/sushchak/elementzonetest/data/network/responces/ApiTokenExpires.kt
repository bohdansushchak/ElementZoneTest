package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class ApiTokenExpires(
    @SerializedName("date")
    val date: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_type")
    val timezoneType: Int
)