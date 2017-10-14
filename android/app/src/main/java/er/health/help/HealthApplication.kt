package er.health.help

import android.app.Application
import er.health.help.module.NetworkModule

class HealthApplication : Application() {

    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .networkModule(NetworkModule())
                .build()

    }

}