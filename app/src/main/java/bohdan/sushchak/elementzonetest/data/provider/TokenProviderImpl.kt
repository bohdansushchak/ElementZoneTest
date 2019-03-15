package bohdan.sushchak.elementzonetest.data.provider

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.ElementZoneApiService
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.internal.TokenNullException
import bohdan.sushchak.elementzonetest.internal.lazyDeffered
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

const val API_TOKEN_KEY = "API_TOKEN"
const val API_TOKEN_EXPIRES = "API_TOKEN_EXPIRES"

class TokenProviderImpl(
    context: Context
) : PreferenceProvider(context = context), TokenProvider, KodeinAware {

    override val kodein by closestKodein(context)
    private val apiService: ElementZoneApiService by instance()

    override val hasSavedToken: Deferred<Boolean> by lazyDeffered {
        !preferences.getString(API_TOKEN_KEY, null).isNullOrEmpty()
    }

    private val _isLoginned = MutableLiveData<Boolean>()
    override val isLoggedIn: LiveData<Boolean>
    get() = _isLoginned

    private val _apiToken = MutableLiveData<String>()

    override val apiToken: LiveData<String>
        get() = _apiToken

    override suspend fun saveToken(loginData: LoginData) {
        GlobalScope.launch(Dispatchers.IO) {
            val jsonTokenExpires = Gson().toJson(loginData.apiTokenExpires)

            preferences.edit().apply {
                putString(API_TOKEN_KEY, loginData.apiToken)
                putString(API_TOKEN_EXPIRES, jsonTokenExpires)
            }.apply()

            _apiToken.postValue(loginData.apiToken)
            _isLoginned.postValue(loginData.apiToken.isNotEmpty())
        }
    }

    override suspend fun refreshToken() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val oldToken = _apiToken.value ?: throw TokenNullException()
                val refreshToken = apiService.refreshTokenAsync(oldToken).await()

            } catch (e: Exception) {

            }
        }
    }

}