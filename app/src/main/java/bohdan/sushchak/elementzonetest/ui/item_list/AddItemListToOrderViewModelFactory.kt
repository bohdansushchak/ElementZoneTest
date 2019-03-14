package bohdan.sushchak.elementzonetest.ui.item_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import bohdan.sushchak.elementzonetest.data.repository.Repository

class AddItemListToOrderViewModelFactory(private val repository: Repository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddItemListToOrderViewModel(repository) as T
    }
}