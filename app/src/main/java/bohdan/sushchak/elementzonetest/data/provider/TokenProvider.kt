package bohdan.sushchak.elementzonetest.data.provider

import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import kotlinx.coroutines.Deferred

interface TokenProvider {

    val hasToken: Boolean

    val needUpdate: Boolean

    val apiToken: String?

    suspend fun saveTokenAsync(loginData: LoginData): Deferred<Unit>

    fun clearToken()
}