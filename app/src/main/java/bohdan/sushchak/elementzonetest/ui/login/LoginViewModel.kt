package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(
    repository: Repository,
    tokenProvider: TokenProvider
) : BaseViewModel(repository) {

    private val _isLoggedIn by lazy { MutableLiveData<Boolean>() }

    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    val hasSavedToken = tokenProvider.hasToken

    fun logIn(email: String, password: String) {
        GlobalScope.launch {
            try {
                val result = repository.logIn(email, password)
                result?.let {
                    _isLoggedIn.postValue(it.apiToken.isNotEmpty())
                }
            } catch (e: IOException) {
                _apiException.postValue(e)
            }
        }
    }
}