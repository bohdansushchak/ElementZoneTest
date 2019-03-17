package bohdan.sushchak.elementzonetest.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import bohdan.sushchak.elementzonetest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import kotlin.coroutines.CoroutineContext

open class BaseFragment : Fragment(), KodeinAware, CoroutineScope {

    override val kodein: Kodein by closestKodein()

    private lateinit var job: Job

    protected val navigationController
    get() = Navigation.findNavController(activity!!, R.id.nav_host_fragment)

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
}