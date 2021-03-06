package bohdan.sushchak.elementzonetest.ui.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddItemListToOrderViewModel(repository: Repository) : BaseViewModel(repository) {

    val productListLive: LiveData<MutableList<String>>
        get() = _productListLive

    private val _productListLive by lazy {
        MutableLiveData<MutableList<String>>().apply {
            value = mutableListOf()
        }
    }

    fun addProduct(productTitle: String) {
        GlobalScope.launch {
            val productList = _productListLive.value
            productList?.add(productTitle)
            _productListLive.postValue(productList)
        }
    }

    fun removeProduct(product: String) {
        GlobalScope.launch {
            val productList = _productListLive.value
            productList?.remove(product)

            _productListLive.postValue(productList)
        }
    }

    suspend fun saveOrder(shopName: String, location: String, date: String, price: Float): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val items = _productListLive.value ?: listOf<String>()

                val order = repository.addOrder( date = date,
                    shopName = shopName,
                    location = location,
                    price = price,
                    items = items)

                val isSuccesfull = order != null

                return@withContext isSuccesfull
            } catch (e: Exception) {
                _apiException.postValue(e)
            }
            return@withContext false
        }
    }
}
