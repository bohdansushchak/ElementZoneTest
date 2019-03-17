package bohdan.sushchak.elementzonetest.ui.orders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrdersViewModel(repository: Repository) : BaseViewModel(repository) {

    val ordersLive: LiveData<List<Order>>
        get() = _ordersLive

    private val _ordersLive = MutableLiveData<List<Order>>()

    fun updateOrders() {
        GlobalScope.launch(Dispatchers.IO) {
            val orderList = repository.getOrders()

            _ordersLive.postValue(orderList)
        }
    }
}
