package bohdan.sushchak.elementzonetest.data.network


import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.MyResponse
import bohdan.sushchak.elementzonetest.data.network.responces.Order

interface RESTService {

    suspend fun logIn(
        email: String,
        password: String
    ): MyResponse<LoginData>?

    suspend fun getOrders(
        offSet: Int,
        limit: Int
    ): MyResponse<List<Order>>?

    suspend fun addOrder(
        date: String,
        location: String,
        price: Float,
        items: List<String>
    ): MyResponse<Order>?

    suspend fun generateLink(
        orderId: Long
    ):  MyResponse<Map<String, String>>?
}