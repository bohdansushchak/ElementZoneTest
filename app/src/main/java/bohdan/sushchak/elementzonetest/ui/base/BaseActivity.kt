package bohdan.sushchak.elementzonetest.ui.base

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import bohdan.sushchak.elementzonetest.R
import bohdan.sushchak.elementzonetest.internal.NoConnectivityException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import java.io.IOException
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

    protected fun toastException(e: IOException) {
        if (e is NoConnectivityException)
            Toast.makeText(this, R.string.err_check_internet_connection, Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
    }
}