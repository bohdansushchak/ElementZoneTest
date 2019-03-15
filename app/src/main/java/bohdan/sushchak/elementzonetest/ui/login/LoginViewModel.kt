package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
) : ViewModel() {

    val isLoginned = repository.getIsLoggedInLive()

    val logInError = MutableLiveData<ApiError>()

    fun logIn(email: String, password: String) {
        GlobalScope.launch{
           val result = repository.logIn(email, password)
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