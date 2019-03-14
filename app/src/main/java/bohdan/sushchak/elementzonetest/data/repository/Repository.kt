package bohdan.sushchak.elementzonetest.data.repository

import androidx.lifecycle.LiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError

interface Repository {

    val apiException: LiveData<ApiError>

    suspend fun logIn(email: String, password: String): LoginData?
}