package bohdan.sushchak.elementzonetest.data.repository


import bohdan.sushchak.elementzonetest.data.network.RESTService

import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.internal.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RepositoryImpl(private var restService: RESTService) : Repository {

    override suspend fun logIn(email: String, password: String): LoginData? {
        return withContext(Dispatchers.IO) {
            return@withContext restService.logIn(email, password)?.data
        }
    }

    override suspend fun getOrders(): List<Order> {
        return withContext(Dispatchers.IO) {
            val orders = restService.getOrders(1, 1)

            return@withContext orders?.data ?: arrayListOf()//TODO fix this
        }
    }

    override suspend fun addOrder(
        date: String,
        location: String,
        price: Float,
        items: List<String>
    ): Order {
        return withContext(Dispatchers.IO) {

            val responce = restService.addOrder(date, location, price, items)

            return@withContext responce?.data!! //TODO fix
        }
    }

    override suspend fun generateLink(orderId: Long): String {
        return withContext(Dispatchers.IO){
            val responce = restService.generateLink(orderId)

            val orderLink = responce?.data?.get("link")

            if (orderLink.isNullOrEmpty())
                return@withContext ""

            return@withContext "${Constants.BASE_URL}/order/$orderLink"
        }
    }
}