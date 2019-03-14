package bohdan.sushchak.elementzonetest.data.network

import bohdan.sushchak.elementzonetest.data.network.responces.LoginResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.util.*


interface ElementZoneApiService {

    @FormUrlEncoded
    @POST("/login")
    fun logInAsync(@Field("email") email: String,
                   @Field("password") password: String
    ):Deferred<Response<LoginResponse>>

    companion object {
        operator fun invoke(
            serverExceptionInterceptor: ServerExceptionInterceptor
        ): ElementZoneApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(serverExceptionInterceptor)

            return Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("https://test.elementzone.uk")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ElementZoneApiService::class.java)
        }
    }
}