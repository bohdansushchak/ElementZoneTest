package bohdan.sushchak.elementzonetest.ui.order_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository

class OrderDetailViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderDetailViewModel(repository) as T
    }
}