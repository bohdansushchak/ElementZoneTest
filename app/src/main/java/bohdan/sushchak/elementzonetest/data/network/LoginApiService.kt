package bohdan.sushchak.elementzonetest.data.network

import androidx.lifecycle.LiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginResponse

interface LoginApiService {

    val apiException: LiveData<ApiError>

    suspend fun logIn(
        email: String,
        password: String
    ): LoginResponse?
}