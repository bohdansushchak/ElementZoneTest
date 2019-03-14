package bohdan.sushchak.elementzonetest.ui.item_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.model.Product
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.add_item_list_to_order_fragment.*
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
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AddItemListToOrderViewModel::class.java)

        fabAddProduct.setOnClickListener { addProduct() }
        bindUI()
    }

    private fun bindUI() = launch {

        viewModel.productListLive.observe(this@AddItemListToOrderFragment, Observer { productList ->
            updateOrdersView(productList)
        })
    }

    private fun updateOrdersView(products: List<Product>) {
        val productItemList = products.toProductItem { position ->
            viewModel.removeProduct(products[position])
        }
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(productItemList)
        }

        rlProduct.apply {
            layoutManager = LinearLayoutManager(this@AddItemListToOrderFragment.context)
            adapter = groupAdapter
        }
    }

    private fun addProduct(){
        val titleProduct = etProductTitle.text.toString()

        if(titleProduct.isBlank()){
            Toast.makeText(this@AddItemListToOrderFragment.context,
                R.string.err_product_title_is_empty,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModel.addProduct(titleProduct)
        etProductTitle.text = null
    }

    private fun List<Product>.toProductItem(onClick: ((position: Int) -> Unit)?): List<ProductItem> {
        return this.map {
            ProductItem(it, onClick)
        }
    }
}
