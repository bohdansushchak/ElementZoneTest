package bohdan.sushchak.elementzonetest.data.provider

import androidx.lifecycle.LiveData
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import kotlinx.coroutines.Deferred

interface TokenProvider {

    val apiToken: LiveData<String>

    val isLoggedIn: LiveData<Boolean>

    val hasSavedToken: Deferred<Boolean>

    suspend fun refreshToken()

    suspend fun saveToken(loginData: LoginData)
}