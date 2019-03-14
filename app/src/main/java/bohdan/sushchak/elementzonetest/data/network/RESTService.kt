package bohdan.sushchak.elementzonetest.data.network

import androidx.lifecycle.LiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.MyResponse
import bohdan.sushchak.elementzonetest.data.network.responces.Order

interface RESTService {

    val apiException: LiveData<ApiError>

    val apiToken: LiveData<String>

    suspend fun logIn(
        email: String,
        password: String
    ): MyResponse<LoginData>?

    suspend fun getOrders(
        offSet: Int,
        limit: Int
    ): MyResponse<List<Order>>?
}