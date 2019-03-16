package bohdan.sushchak.elementzonetest.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class OrdersFragment : BaseFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private lateinit var viewModel: OrdersViewModel
    private val viewModelFactory: OrdersViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OrdersViewModel::class.java)

        fabCreateOrder.setOnClickListener { navigateTo(R.id.action_orders_to_createOrderFragment) }
        bindUI()
    }

    private fun bindUI() = launch {
        viewModel.ordersLive.observe(this@OrdersFragment, Observer { orders ->
            initRecyclerView(orders)
        })
    }

    private fun initRecyclerView(orders: List<Order>) {

        val items = orders.toOrderItems()

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        groupAdapter.setOnItemClickListener { item, _ ->
            val index = items.indexOf(item)
            val selectedOrder = orders[index]
            startAboutFragment(selectedOrder)
        }

        rlOrders.apply {
            layoutManager = LinearLayoutManager(this@OrdersFragment.context)
            adapter = groupAdapter
        }
    }

    private fun List<Order>.toOrderItems(): List<OrderItem> {
        return this.map { order ->
            OrderItem(order)
        }
    }

    private fun startAboutFragment(order: Order) {

        navigateTo(R.id.action_orders_to_aboutOrderFragment)
    }
}
