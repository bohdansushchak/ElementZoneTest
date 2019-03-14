package bohdan.sushchak.elementzonetest.data.network

import bohdan.sushchak.elementzonetest.data.network.model.OrderModel
import bohdan.sushchak.elementzonetest.data.network.model.Product
import bohdan.sushchak.elementzonetest.data.network.responces.LoginData
import bohdan.sushchak.elementzonetest.data.network.responces.MyResponse
import bohdan.sushchak.elementzonetest.data.network.responces.Order
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

interface ElementZoneApiService {

    @FormUrlEncoded
    @POST("/login")
    fun logInAsync(@Field("email") email: String,
                   @Field("password") password: String
    ):Deferred<Response<MyResponse<LoginData>>>

    @FormUrlEncoded
    @POST("/refresh")
    fun refreshTokenAsync(@Field("api_token") apiToken: String
    ):Deferred<Response<MyResponse<String>>>

    @FormUrlEncoded
    @POST("/orders")
    fun getOrdersAsync(@Field("api_token") apiToken: String
    ):Deferred<Response<MyResponse<List<Order>>>>

    @FormUrlEncoded
    @POST("/addOrder")
    fun addOrderAsync(@Field("api_token") apiToken: String,
                      @Field("date") date: String,
                      @Field("location") location: String,
                      @Field("price") price: Float,
                      @Field("items") items: List<Product>
    ):Deferred<Response<MyResponse<String>>>

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