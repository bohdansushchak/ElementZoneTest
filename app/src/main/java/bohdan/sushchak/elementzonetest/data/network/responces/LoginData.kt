package bohdan.sushchak.elementzonetest.data.network.responces

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("api_token")
    val apiToken: String,

    @SerializedName("api_token_expires")
    val apiTokenExpires: ApiTokenExpires,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("updated_at")
    val updatedAt: String
)