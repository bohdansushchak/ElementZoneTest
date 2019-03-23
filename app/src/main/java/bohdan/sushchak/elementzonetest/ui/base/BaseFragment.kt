package bohdan.sushchak.elementzonetest.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.internal.BadRequestException
import bohdan.sushchak.elementzonetest.internal.NoConnectivityException
import bohdan.sushchak.elementzonetest.internal.UnauthorizedException
import bohdan.sushchak.elementzonetest.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein()

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    protected fun toastException(e: IOException) {
        if (e is NoConnectivityException)
            Toast.makeText(context, R.string.err_check_internet_connection, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }

    protected fun getNavController(view: View): NavController {
        val navController = Navigation.findNavController(view)
        navController.setGraph(R.navigation.app_navigation)
        return navController
    }

    protected fun handleException(e: Exception){
        when(e){
            is NoConnectivityException ->
                Toast.makeText(context, R.string.err_check_internet_connection, Toast.LENGTH_SHORT).show()

            is BadRequestException ->
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            is UnauthorizedException -> startLoginActivity()
        }
    }

    private fun startLoginActivity() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity?.startActivity(intent)
        activity?.finish()
    }
}