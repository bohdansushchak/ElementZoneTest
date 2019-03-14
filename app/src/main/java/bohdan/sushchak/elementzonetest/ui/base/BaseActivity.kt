package bohdan.sushchak.elementzonetest.ui.base

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import kotlin.coroutines.CoroutineContext

@SuppressLint("Registered")
open class BaseActivity: AppCompatActivity(), CoroutineScope, KodeinAware {

    override val kodein by closestKodein()

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
}