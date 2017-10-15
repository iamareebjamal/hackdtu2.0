package er.health.help

import dagger.Component
import er.health.help.module.AndroidModule
import er.health.help.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(app: HealthApplication)

    fun inject(activity: MainActivity)

}
