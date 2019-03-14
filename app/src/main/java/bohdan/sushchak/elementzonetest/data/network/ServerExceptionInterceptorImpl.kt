package bohdan.sushchak.elementzonetest.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ServerExceptionInterceptorImpl : ServerExceptionInterceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val response = chain.proceed(request)

        if(response.code() == 500) {
            Log.e("CODE500", response.message())
        }

        return response
    }
}