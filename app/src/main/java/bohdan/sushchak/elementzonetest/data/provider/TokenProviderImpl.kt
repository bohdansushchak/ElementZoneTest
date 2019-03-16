package bohdan.sushchak.elementzonetest.data.provider

import android.annotation.SuppressLint
import android.content.Context
import bohdan.sushchak.elementzonetest.data.network.ElementZoneApiService
import bohdan.sushchak.elementzonetest.data.network.responces.ApiTokenExpires
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.internal.Constants
import bohdan.sushchak.elementzonetest.internal.Constants.STR_EMPTY
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance
import java.text.SimpleDateFormat
import java.util.*

const val API_TOKEN_KEY = "API_TOKEN"
const val API_TOKEN_EXPIRES = "API_TOKEN_EXPIRES"

class TokenProviderImpl(
    context: Context
) : PreferenceProvider(context = context), TokenProvider, KodeinAware {

    override val kodein by closestKodein(context)
    private val apiService: ElementZoneApiService by instance()

    override val needUpdate: Boolean
        get() = isNeedUpdateToken(apiTokenExpires)

    override
    val hasToken: Boolean
        get() = apiToken != STR_EMPTY

    private lateinit var apiToken: String

    private var apiTokenExpires: ApiTokenExpires? = null

    override val apiTokenAsync: Deferred<String>
        get() = GlobalScope.async {
            val token = apiToken

            if (needUpdate)
                refreshToken()

            return@async token
        }

    init {
        GlobalScope.launch {
            apiToken = preferences.getString(API_TOKEN_KEY, STR_EMPTY) ?: STR_EMPTY
            val apiTokenExpiresJson = preferences.getString(API_TOKEN_EXPIRES, null)

            apiTokenExpiresJson?.let {
                Gson().fromJson(it, ApiTokenExpires::class.java)
            }
        }
    }

    override suspend fun saveToken(loginData: LoginData) {
        GlobalScope.launch(Dispatchers.IO) {
            val jsonTokenExpires = Gson().toJson(loginData.apiTokenExpires)

            apiToken = loginData.apiToken
            apiTokenExpires = loginData.apiTokenExpires

            preferences.edit().apply {
                putString(API_TOKEN_KEY, loginData.apiToken)
                putString(API_TOKEN_EXPIRES, jsonTokenExpires)
            }.apply()
        }
    }

    override suspend fun refreshToken(): Boolean {
        return withContext(Dispatchers.IO) {
            val response = apiService.refreshTokenAsync(apiToken).await()

            return@withContext response.isSuccessful && response.body()?.data!!
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isNeedUpdateToken(tokenExpires: ApiTokenExpires?): Boolean {
        if (tokenExpires?.date.isNullOrEmpty())
            return true

        val apiDateSdf = SimpleDateFormat(Constants.API_DATE_FORMAT_PATTERN)
        val dateTokenExpires = apiDateSdf.parse(tokenExpires?.date)

        val currentDate = Date()
//TODO fix: add comparing with time zone
        return currentDate.before(dateTokenExpires)
    }

    override suspend fun clearToken() {
        GlobalScope.launch {
            preferences.edit().apply {
                remove(API_TOKEN_KEY)
                remove(API_TOKEN_EXPIRES)
            }.apply()

            apiToken = STR_EMPTY
        }
    }
}