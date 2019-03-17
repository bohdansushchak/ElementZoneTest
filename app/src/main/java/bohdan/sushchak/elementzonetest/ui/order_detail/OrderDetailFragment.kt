package bohdan.sushchak.elementzonetest.ui.order_detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.responces.Item
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.internal.normalizeDate
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.order_detail_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class OrderDetailFragment : BaseFragment() {

    private lateinit var viewModel: OrderDetailViewModel
    private val viewModelFactory: OrderDetailViewModelFactory by instance()

    private val args by navArgs<OrderDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.order_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(OrderDetailViewModel::class.java)

        val order = args.order

        btnShare.setOnClickListener { generateLink(order) }
        bindUI(order)
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(order: Order) = launch(Dispatchers.Main) {

        tvShopTitle.text = "Title id: ${order.id}"
        tvLocation.text = order.location

        val normalDate = normalizeDate(order.date)

        tvDate.text = normalDate
        tvPrice.text = "${order.price}zł"

        initListOfItems(order.items)
    }

    private fun initListOfItems(items: List<Item>) {

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items.toProductItem())
        }

        rvProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
        }
    }

    private fun List<Item>.toProductItem(): List<ProductItem>{
        return this.map { ProductItem(it) }
    }

    private fun generateLink(order: Order) {
        GlobalScope.launch(Dispatchers.Main) {
            val url = viewModel.generateLink(order.id)
            share(url)
        }
    }

    private fun share(url: String){
        val intentSend = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }

        activity?.packageManager?.let {
            if(intentSend.resolveActivity(it) != null)
                startActivity(intentSend)
            else
                Toast.makeText(context, R.string.err_not_exist_app_to_send, Toast.LENGTH_SHORT).show()
        }
    }
}
