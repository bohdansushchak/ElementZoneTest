package bohdan.sushchak.elementzonetest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.MyResponse
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.internal.NoConnectivityException
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class RESTServiceImpl(
    private val elementZoneApiService: ElementZoneApiService
) : RESTService {

    private val _apiToken = MutableLiveData<String>()
    override val apiToken: LiveData<String>
        get() = _apiToken

    private val _apiException = MutableLiveData<ApiError>()
    override val apiException: LiveData<ApiError>
        get() = _apiException

    override suspend fun logIn(email: String, password: String): MyResponse<LoginData>? {
        val loginResponseDeff = elementZoneApiService.logInAsync(email, password)
        val response = loginResponseDeff.await()

        try {
            if (response.isSuccessful) {
                if (response.body()?.data?.apiToken != null)
                    updateApiToken(response.body()?.data?.apiToken!!)
                return response.body()
            }

            fetchException(response = response)
        } catch (e: NoConnectivityException) {

        } catch (e: HttpException) {
            fetchException(response)
        }
        return null
    }

    override suspend fun getOrders(offSet: Int, limit: Int): MyResponse<List<Order>>? {
        val apiToken = apiToken.value
        val responseOrderListDeff = elementZoneApiService.getOrdersAsync(apiToken!!)
        val response = responseOrderListDeff.await()

        try {
            if (response.isSuccessful)
                return response.body()
            fetchException(response = response)
        } catch (e: NoConnectivityException) {

        } catch (e: HttpException) {
            fetchException(response = response)
        }
        return null
    }

    private fun <T : Any> fetchException(response: Response<MyResponse<T>>) {
        GlobalScope.launch {
            try {
                val strJson = response.errorBody()?.string()
                val apiError = Gson().fromJson(strJson, MyResponse::class.java).error

                _apiException.postValue(apiError)
            } catch (e: Exception) {
                Log.e("ERR", e.message, e)
            }
        }
    }

    private suspend fun updateApiToken(apiToken: String) {
        _apiToken.postValue(apiToken)
    }
}

