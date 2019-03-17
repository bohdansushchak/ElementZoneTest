package bohdan.sushchak.elementzonetest.data.provider

import android.annotation.SuppressLint
import android.content.Context
import bohdan.sushchak.elementzonetest.data.network.responces.ApiTokenExpires
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.internal.Constants
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.text.SimpleDateFormat
import java.util.*

const val API_TOKEN_KEY = "API_TOKEN"
const val API_TOKEN_EXPIRES = "API_TOKEN_EXPIRES"

class TokenProviderImpl(
    context: Context
) : PreferenceProvider(context = context), TokenProvider {
    override val needUpdate: Boolean
        get() = isNeedUpdateToken()

    override var apiToken: String? = preferences.getString(API_TOKEN_KEY, null)

    override val hasToken: Boolean
        get() = !apiToken.isNullOrEmpty()

    private var apiTokenExpires: ApiTokenExpires? = initTokenExpires()

    override suspend fun saveTokenAsync(loginData: LoginData): Deferred<Unit> {
        return GlobalScope.async(Dispatchers.IO) {
            val jsonTokenExpires = Gson().toJson(loginData.apiTokenExpires)

            apiToken = loginData.apiToken
            apiTokenExpires = loginData.apiTokenExpires

            preferences.edit().apply {
                putString(API_TOKEN_KEY, loginData.apiToken)
                putString(API_TOKEN_EXPIRES, jsonTokenExpires)
            }.apply()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun isNeedUpdateToken(): Boolean {
        if (apiTokenExpires == null)
            initTokenExpires()

        if (apiTokenExpires?.date.isNullOrEmpty())
            return true

        val apiDateSdf = SimpleDateFormat(Constants.API_DATE_FORMAT_PATTERN)
        val dateTokenExpires = apiDateSdf.parse(apiTokenExpires?.date)

        val currentDate = Date()
//TODO fix: add comparing with time zone
        return currentDate.before(dateTokenExpires)
    }

    override fun clearToken()
    {
        apiToken = null
        apiTokenExpires = null

        preferences.edit().apply {
            remove(API_TOKEN_KEY)
            remove(API_TOKEN_EXPIRES)
        }.apply()
    }

    private fun initTokenExpires(): ApiTokenExpires? {
        val apiTokenExpiresJson = preferences.getString(API_TOKEN_EXPIRES, null) ?: return null

        return Gson().fromJson(apiTokenExpiresJson, ApiTokenExpires::class.java)
    }
}