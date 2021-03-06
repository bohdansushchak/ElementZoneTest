package bohdan.sushchak.elementzonetest.ui.add_order

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.internal.Constants
import bohdan.sushchak.elementzonetest.internal.formatDate
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import kotlinx.android.synthetic.main.create_order_fragment.*
import java.util.*

class CreateOrderFragment : BaseFragment() {

    private lateinit var viewModel: CreateOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this)
            .get(CreateOrderViewModel::class.java)

        btnNextStep.setOnClickListener { nextStep(it) }
        etDate.setOnClickListener { pickDate() }
    }

    private fun nextStep(view: View) {
        val shopTitle = etShopTitle.text.toString()
        val shopLocation = etShopLocation.text.toString()
        val date = etDate.text.toString()

        if (shopTitle.isBlank() || shopLocation.isBlank() || date.isBlank()) {
            etShopTitleLayout.error = if (shopTitle.isBlank()) getString(R.string.err_shop_title_is_blank) else null
            etShopLocationLayout.error =
                if (shopLocation.isBlank()) getString(R.string.err_shop_location_is_blank) else null
            etDateLayout.error = if (date.isBlank()) getString(R.string.err_shop_date_is_blank) else null
            return
        }

        val actionAddProducts = CreateOrderFragmentDirections
            .actionAddItemsToOrder(
                shopTitle,
                shopLocation,
                viewModel.apiDate
            )
        Navigation.findNavController(view).navigate(actionAddProducts)
    }

    private fun pickDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        activity?.let {
            val dpd = DatePickerDialog(it, DatePickerDialog.OnDateSetListener { _, year_, monthOfYear, dayOfMonth ->

                calendar.clear()
                calendar.set(Calendar.YEAR, year_)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                viewModel.apiDate = formatDate(calendar.time, Constants.API_DATE_FORMAT_PATTERN)
                etDate.setText(formatDate(calendar.time, Constants.DATE_FORMAT_PATTERN))

            }, year, month, day)
            dpd.show()
        }
    }
}
