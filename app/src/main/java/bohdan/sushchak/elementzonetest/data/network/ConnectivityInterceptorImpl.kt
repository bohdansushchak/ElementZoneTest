package bohdan.sushchak.elementzonetest.data.network

import android.content.Context
import android.net.ConnectivityManager
import bohdan.sushchak.elementzonetest.internal.NoConnectivityException
import bohdan.sushchak.elementzonetest.internal.UnathorizedException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NoConnectivityException()

        val response = chain.proceed(chain.request())

        return response
    }

    private fun isOnline(): Boolean{
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager

        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}