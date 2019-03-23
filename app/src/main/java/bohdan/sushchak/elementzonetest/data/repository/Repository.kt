package bohdan.sushchak.elementzonetest.data.repository

import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.Order

interface Repository {

    suspend fun logIn(email: String,
                      password: String
    ): LoginData?

    suspend fun getOrders(): List<Order>?

    suspend fun addOrder(
        date: String,
        location: String,
        shopName: String,
        price: Float,
        items: List<String>
    ): Order?

    suspend fun generateLink(orderId: Long): String?
}