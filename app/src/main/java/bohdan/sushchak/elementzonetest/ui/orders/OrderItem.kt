package bohdan.sushchak.elementzonetest.ui.orders

import android.annotation.SuppressLint
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.data.network.responces.Order
import bohdan.sushchak.elementzonetest.internal.normalizeDate
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.order_item.*

class OrderItem(private val order: Order
): Item() {
    @SuppressLint("SetTextI18n")
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            tvOrderTitle.text = order.shopName
            tvOrderDate.text = normalizeDate(order.date)
        }
    }

    override fun getLayout() = R.layout.order_item
}