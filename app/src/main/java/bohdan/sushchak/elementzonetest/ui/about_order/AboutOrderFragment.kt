package bohdan.sushchak.elementzonetest.ui.about_order

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.ui.base.BaseFragment
import kotlinx.coroutines.launch
import org.kodein.di.generic.instance

class AboutOrderFragment : BaseFragment() {

    private lateinit var viewModel: AboutOrderViewModel
    private val viewModelFactory: AboutOrderViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_order_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AboutOrderViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {

    }
}
