package bohdan.sushchak.elementzonetest.data.network

import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.MyResponse
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.internal.BadRequestException
import bohdan.sushchak.elementzonetest.internal.UnauthorizedException
import com.google.gson.Gson
import retrofit2.Response

class RESTServiceImpl(
    private val elementZoneApiService: ElementZoneApiService,
    private val tokenProvider: TokenProvider
) : RESTService {

    override suspend fun logIn(email: String, password: String): MyResponse<LoginData>? {
        val response = elementZoneApiService.logInAsync(email, password).await()

        if (response.isSuccessful) {

            val loginData = response.body()?.data!!
            tokenProvider.saveTokenAsync(loginData).await()

            return response.body()
        }
        fetchError(response)

        return null
    }

    override suspend fun getOrders(offSet: Int, limit: Int): MyResponse<List<Order>>? {
        val apiToken = getTokenAsync()
        val responseOrderListDeff = elementZoneApiService.getOrdersAsync(apiToken)
        val response = responseOrderListDeff.await()

        if (response.isSuccessful)
            return response.body()
        fetchError(response = response)

        return null
    }

    private fun <T : Any> fetchError(response: Response<MyResponse<T>>) {
        val strJson = response.errorBody()?.string()
        val apiError = Gson().fromJson(strJson, MyResponse::class.java).error

        if (apiError?.code == 401){
            tokenProvider.clearToken()
            throw UnauthorizedException()
        }

        throw BadRequestException(apiError?.message)
    }

    override suspend fun addOrder(
        date: String,
        shopName: String,
        location: String,
        price: Float,
        items: List<String>
    ): MyResponse<Order>? {

        val token = getTokenAsync()
        val response = elementZoneApiService.addOrderAsync(
            token,
            date,
            location,
            price,
            items,
            shopName
        ).await()

        if (response.isSuccessful)
            return response.body()
        fetchError(response = response)
        return null
    }

    override suspend fun generateLink(orderId: Long): MyResponse<Map<String, String>>? {

        val token = getTokenAsync()
        val response = elementZoneApiService.generateLinkAsync(token, orderId).await()

        if (response.isSuccessful)
            return response.body()
        fetchError(response)

        return null
    }

    private suspend fun getTokenAsync(shouldRefresh: Boolean = false): String {
        suspend fun refreshToken() {
            val token = tokenProvider.apiToken

            val response = elementZoneApiService.refreshTokenAsync(token!!).await()

            if (!response.isSuccessful)
                fetchError(response)
        }

        if (tokenProvider.apiToken.isNullOrEmpty())
            throw UnauthorizedException()

        if (tokenProvider.needUpdate || !shouldRefresh)
            refreshToken()

        return tokenProvider.apiToken!!
    }
}

