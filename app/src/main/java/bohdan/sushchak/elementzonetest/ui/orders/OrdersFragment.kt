package bohdan.sushchak.elementzonetest.ui.orders

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.internal.UnathorizedException
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import bohdan.sushchak.elementzonetest.ui.login.LoginActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.orders_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import java.io.IOException

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

        viewModel.updateOrders()

        fabCreateOrder.setOnClickListener {
            getNavController(it).navigate(R.id.actionCreateOrder)
        }

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        viewModel.ordersLive.observe(this@OrdersFragment, Observer { orders ->
            initRecyclerView(orders)
        })

        viewModel.apiException.observe(this@OrdersFragment, Observer { exception ->
            handleException(exception)
        })
    }

    private fun initRecyclerView(orders: List<Order>) {

        val items = orders.toOrderItems()

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        groupAdapter.setOnItemClickListener { item, view ->
            val index = items.indexOf(item)
            val selectedOrder = orders[index]
            startOrderDetailFragment(view, selectedOrder)
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

    private fun startOrderDetailFragment(view: View, order: Order) {
        val action = OrdersFragmentDirections.actionDetailOrder(order)
        getNavController(view).navigate(action)
    }
}
