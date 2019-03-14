package bohdan.sushchak.elementzonetest

import android.app.Application
import bohdan.sushchak.elementzonetest.data.network.*
import bohdan.sushchak.elementzonetest.data.repository.Repository
import bohdan.sushchak.elementzonetest.data.repository.RepositoryImpl
import bohdan.sushchak.elementzonetest.ui.about_order.AboutOrderViewModelFactory
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

        bind<ServerExceptionInterceptor>() with singleton { ServerExceptionInterceptorImpl() }

        bind() from singleton { ElementZoneApiService(instance()) }

        bind<RESTService>() with singleton {  RESTServiceImpl(instance())}
        bind<Repository>() with singleton { RepositoryImpl(instance()) }

        bind() from provider { LoginViewModelFactory(instance())}
        bind() from provider { OrdersViewModelFactory(instance()) }
        bind() from provider { AddItemListToOrderViewModelFactory(instance()) }
        bind() from provider { AboutOrderViewModelFactory(instance()) }
    }
}