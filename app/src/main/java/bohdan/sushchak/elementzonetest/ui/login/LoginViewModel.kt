package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.internal.lazyDeffered
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository,
    private val tokenProvider: TokenProvider
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()

    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    val hasSavedToken by lazyDeffered {
        tokenProvider.run {

            var hasActualToken = hasToken && !needUpdate

            if (!hasActualToken)
                hasActualToken = refreshToken()
            return@run hasActualToken
        }
    }

    val logInError = MutableLiveData<ApiError>()

    fun logIn(email: String, password: String) {
        GlobalScope.launch {
            val result = repository.logIn(email, password)
            result?.let {
                _isLoggedIn.postValue(it.apiToken.isNotEmpty())
            }
        }
    }

    init {
        repository.apply {
            apiException.observeForever {
                logInError.postValue(it)
            }
        }
    }
}