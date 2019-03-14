package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("data")
    val data: LoginData?,

    @SerializedName("error")
    val error: ApiError?
)