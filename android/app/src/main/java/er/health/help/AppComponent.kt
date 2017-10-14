package er.health.help

import dagger.Component
import er.health.help.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NetworkModule::class))
interface AppComponent {

    fun inject(healthApplication: HealthApplication)

}
