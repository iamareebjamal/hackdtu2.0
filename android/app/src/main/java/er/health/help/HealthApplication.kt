package er.health.help

import android.app.Application
import android.util.Log
import er.health.help.data.HealthService
import er.health.help.module.AndroidModule
import er.health.help.module.NetworkModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HealthApplication : Application() {

    @Inject
    lateinit var healthService : HealthService

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .androidModule(AndroidModule(this))
                .networkModule(NetworkModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }

}