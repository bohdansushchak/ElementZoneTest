package bohdan.sushchak.elementzonetest.data.provider

import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import kotlinx.coroutines.Deferred

interface TokenProvider {

    //var apiToken: String

    val hasToken: Boolean

    val needUpdate: Boolean

    val apiTokenAsync: Deferred<String>

    suspend fun refreshToken(): Boolean

    suspend fun saveToken(loginData: LoginData)

    suspend fun clearToken()
}