package bohdan.sushchak.elementzonetest.ui.item_list

import bohdan.sushchak.elementzonetest.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.product_item.*

class ProductItem(private val product: String,
                  private var onClick: ((position: Int) -> Unit)? = null): Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
       viewHolder.apply {
           tvProductTitle.text = product

           ivRemove.setOnClickListener {
                onClick?.invoke(position)
           }
       }
    }

    override fun getLayout() = R.layout.product_item
}