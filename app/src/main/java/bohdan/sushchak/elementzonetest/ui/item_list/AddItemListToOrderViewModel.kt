package bohdan.sushchak.elementzonetest.ui.item_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.model.Product
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddItemListToOrderViewModel(repository: Repository) : BaseViewModel(repository) {

    val productListLive :LiveData<MutableList<Product>>
    get() = _productListLive

    private val _productListLive = MutableLiveData<MutableList<Product>>()

    init {
        _productListLive.value = mutableListOf()
    }

    fun addProduct(productTitle: String) {
        GlobalScope.launch {
            val productList = _productListLive.value
            productList?.add(Product(productTitle))

            _productListLive.postValue(productList)
        }
    }

    fun removeProduct(product: Product) {
        GlobalScope.launch {
            val productList = _productListLive.value
            productList?.remove(product)

            _productListLive.postValue(productList)
        }
    }

    fun saveOrder() {

    }
}
