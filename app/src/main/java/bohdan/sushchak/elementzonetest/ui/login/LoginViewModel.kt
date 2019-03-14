package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import bohdan.sushchak.elementzonetest.data.network.responces.ApiError
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    val apiKey: MutableLiveData<LoginData?> = MutableLiveData()
    val logInError = MutableLiveData<ApiError>()

    fun logIn(email: String, password: String) {
        GlobalScope.launch{
            val response = repository.logIn(email, password)
            apiKey.postValue(response)
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