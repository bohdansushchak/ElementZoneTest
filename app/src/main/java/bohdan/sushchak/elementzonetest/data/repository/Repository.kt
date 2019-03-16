package bohdan.sushchak.elementzonetest.data.repository

import androidx.lifecycle.LiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.Order

interface Repository {

    val apiException: LiveData<ApiError>

    suspend fun logIn(email: String, password: String): LoginData?

    suspend fun getOrders(): List<Order>
}