package bohdan.sushchak.elementzonetest.ui.about_order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository

class AboutOrderViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AboutOrderViewModel(repository) as T
    }
}