package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.internal.lazyDeffered
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(
    repository: Repository,
    private val tokenProvider: TokenProvider
) : BaseViewModel(repository) {

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

    fun logIn(email: String, password: String) {
        GlobalScope.launch {
            val result = repository.logIn(email, password)
            result?.let {
                _isLoggedIn.postValue(it.apiToken.isNotEmpty())
            }
        }
    }
}