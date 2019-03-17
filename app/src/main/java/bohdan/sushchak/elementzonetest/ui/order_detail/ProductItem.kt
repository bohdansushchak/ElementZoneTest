package bohdan.sushchak.elementzonetest.ui.order_detail

import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.responces.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.product_item.*


class ProductItem(private val product: Item): com.xwray.groupie.kotlinandroidextensions.Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.apply {
           tvProductTitle.text = product.item
       }
    }

    override fun getLayout() = R.layout.order_detail_product_item
}