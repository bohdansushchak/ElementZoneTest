package bohdan.sushchak.elementzonetest.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import bohdan.sushchak.elementzonetest.R
import kotlinx.android.synthetic.main.activity_main.*

const val IS_HIDE_ACTION_BAR_KEY = "isHideActionBar"

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actionBar?.customView = toolbar
        actionBar?.isHideOnContentScrollEnabled
        actionBar?.hide()

        navController = Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener(this@MainActivity)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        val title = destination.label
        if (!TextUtils.isEmpty(title))
            tvToolbarTitle.text = title

        val isHideActionBar = destination.arguments[IS_HIDE_ACTION_BAR_KEY]?.defaultValue as Boolean?

        if (isHideActionBar == true)
            toolbar.visibility = View.GONE
        else
            toolbar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        navController.removeOnDestinationChangedListener(this@MainActivity)
    }
}
