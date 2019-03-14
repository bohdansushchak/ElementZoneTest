package bohdan.sushchak.elementzonetest.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginResponse
import bohdan.sushchak.elementzonetest.internal.NoConnectivityException
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginApiServiceImpl(
    private val elementZoneApiService: ElementZoneApiService
) : LoginApiService {

    private val _apiException = MutableLiveData<ApiError>()

    override val apiException: LiveData<ApiError>
        get() = _apiException

    override suspend fun logIn(email: String, password: String): LoginResponse? {
        val loginResponseDeff = elementZoneApiService.logInAsync(email, password)
        val response = loginResponseDeff.await()

        try {
            if (response.isSuccessful)
                return response.body()

            fetchException(response)
        } catch (e: NoConnectivityException) {

        } catch (e: Exception) {
            fetchException(response)
        }
        return null
    }

    private fun fetchException(response: Response<LoginResponse>) {
        GlobalScope.launch {
            try {
                val strJson = response.errorBody()?.string()
                val apiError = Gson().fromJson(strJson, LoginResponse::class.java).error

                _apiException.postValue(apiError)
            }
            catch (e: Exception){
                Log.e("ERR", e.message, e)
            }
        }
    }
}

