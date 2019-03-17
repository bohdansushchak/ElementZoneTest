package bohdan.sushchak.elementzonetest.ui.order_detail

import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.withContext

class OrderDetailViewModel(repository: Repository) : BaseViewModel(repository) {

    suspend fun generateLink(): String {
        return withContext(Dispatchers.IO) {

            return@withContext ""
        }
    }
}
