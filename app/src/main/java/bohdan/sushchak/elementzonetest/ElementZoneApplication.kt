package bohdan.sushchak.elementzonetest

import android.app.Application
import bohdan.sushchak.elementzonetest.data.network.*
import bohdan.sushchak.elementzonetest.data.provider.TokenProvider
import bohdan.sushchak.elementzonetest.data.provider.TokenProviderImpl
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.data.repository.RepositoryImpl
import bohdan.sushchak.elementzonetest.ui.order_detail.OrderDetailViewModelFactory
import bohdan.sushchak.elementzonetest.ui.item_list.AddItemListToOrderViewModelFactory
import bohdan.sushchak.elementzonetest.ui.login.LoginViewModelFactory
import bohdan.sushchak.elementzonetest.ui.orders.OrdersViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class ElementZoneApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ElementZoneApplication))

        bind() from singleton { ElementZoneApiService(instance()) }

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind<RESTService>() with singleton {  RESTServiceImpl(instance(), instance())}
        bind<Repository>() with singleton { RepositoryImpl(instance()) }

        bind<TokenProvider>() with singleton { TokenProviderImpl(instance()) }

        bind() from provider { LoginViewModelFactory(instance(), instance())}
        bind() from provider { OrdersViewModelFactory(instance()) }
        bind() from provider { AddItemListToOrderViewModelFactory(instance()) }
        bind() from provider { OrderDetailViewModelFactory(instance()) }
    }
}