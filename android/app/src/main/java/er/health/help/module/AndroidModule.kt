package er.health.help.module

import dagger.Module
import dagger.Provides
import er.health.help.HealthApplication
import javax.inject.Singleton

@Module
class AndroidModule(private val app: HealthApplication) {
    @Provides @Singleton
    fun provideApplication() = app
}