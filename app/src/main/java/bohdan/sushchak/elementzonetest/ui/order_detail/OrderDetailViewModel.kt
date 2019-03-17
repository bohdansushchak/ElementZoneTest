package bohdan.sushchak.elementzonetest.ui.order_detail

import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class OrderDetailViewModel(repository: Repository) : BaseViewModel(repository) {

    suspend fun generateLink(orderId: Long): String? {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext repository.generateLink(orderId)
            }
            catch (e: IOException){
                _apiException.postValue(e)
            }
            return@withContext null
        }
    }
}
