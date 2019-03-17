package bohdan.sushchak.elementzonetest.ui.item_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.internal.LostArgumentsException
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.add_item_list_to_order_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class AddItemListToOrderFragment : BaseFragment() {

    private lateinit var viewModel: AddItemListToOrderViewModel
    private val viewModelFactory: AddItemListToOrderViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_item_list_to_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { AddItemListToOrderFragmentArgs.fromBundle(it) }
        val shopTitle = safeArgs?.shopTitle ?: throw LostArgumentsException()
        val location = safeArgs.location
        val date = safeArgs.date

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddItemListToOrderViewModel::class.java)

        btnSaveOrder.setOnClickListener {
            saveOrder(shopTitle, location, date)
        }
        fabAddProduct.setOnClickListener { addProduct() }

        bindUI()
    }

    private fun bindUI() = launch {

        viewModel.productListLive.observe(this@AddItemListToOrderFragment, Observer { productList ->
            updateOrdersView(productList)
        })
    }

    private fun updateOrdersView(products: List<String>) {
        val productItemList = products.map {
            ProductItem(it) { position ->
                viewModel.removeProduct(products[position])
            }
        }

        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(productItemList)
        }

        rlProduct.apply {
            layoutManager = LinearLayoutManager(this@AddItemListToOrderFragment.context)
            adapter = groupAdapter
        }
    }

    private fun addProduct() {
        val titleProduct = etProductTitle.text.toString()

        if (titleProduct.isBlank()) {
            Toast.makeText(
                this@AddItemListToOrderFragment.context,
                R.string.err_product_title_is_empty,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.addProduct(titleProduct)
        etProductTitle.text = null
    }

    private fun saveOrder(shopTitle: String, location: String, date: String) {
        val priceText = etProductPrice.text.toString()

        if (priceText.isBlank()) {
            Toast.makeText(context, R.string.err_price_is_empty, Toast.LENGTH_SHORT).show()
            return
        }

        val price = priceText.toFloat()

        launch(Dispatchers.Main) {
            val isSucc = viewModel.saveOrder(shopTitle, location, date, price)

            if(isSucc)
            {
                val navigationController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)
                navigationController.navigate(R.id.action_addItemListToOrderFragment_to_orders,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.orders,
                            false)
                        .build()
                )
                navigationController.popBackStack(R.id.orders, true)
            }
        }
    }
}
