package bohdan.sushchak.elementzonetest.data.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.LoginApiService
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private var loginApiService: LoginApiService) : Repository {

    val _apiException = MutableLiveData<ApiError>()
    override val apiException: LiveData<ApiError>
        get() = _apiException

    override suspend fun logIn(email: String, password: String): LoginData? {
        return withContext(Dispatchers.IO) {

            val loginResponse = loginApiService.logIn(email, password)?.data

            return@withContext loginResponse
        }
    }

    init {
        loginApiService.apiException.observeForever {
            _apiException.postValue(it)
        }
    }
}