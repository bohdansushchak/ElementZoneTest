package bohdan.sushchak.elementzonetest.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.RESTService
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private var restService: RESTService) : Repository {

    val _apiException = MutableLiveData<ApiError>()
    override val apiException: LiveData<ApiError>
        get() = _apiException

    init {
        restService.apiException.observeForever {
            _apiException.postValue(it)
        }
    }

    override suspend fun logIn(email: String, password: String): LoginData? {
        return withContext(Dispatchers.IO) {

            val loginResponse = restService.logIn(email, password)?.data

            return@withContext loginResponse
        }
    }

    override suspend fun getOrders(): List<Order> {
        return withContext(Dispatchers.IO) {
            val orders = restService.getOrders(1,1)

            return@withContext orders?.data!! //TODO fix this
        }
    }

    override fun getIsLoggedInLive(): LiveData<Boolean> {
        return restService.getIsLoggedInLive()
    }
}