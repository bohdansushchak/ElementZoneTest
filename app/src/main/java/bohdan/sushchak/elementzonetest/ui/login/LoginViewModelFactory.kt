package bohdan.sushchak.elementzonetest.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository


class LoginViewModelFactory(
    private val repository: Repository,
    private val tokenProvider: TokenProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(repository, tokenProvider) as T
    }
}