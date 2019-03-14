package bohdan.sushchak.elementzonetest.ui.orders

import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.internal.lazyDeffered
import bohdan.sushchak.elementzonetest.ui.base.BaseViewModel

class OrdersViewModel(repository: Repository) : BaseViewModel(repository) {

    val orders by lazyDeffered {
        repository.getOrders()
    }
}
