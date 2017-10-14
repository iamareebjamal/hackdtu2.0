package er.health.help.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import dagger.Module
import dagger.Provides
import er.health.help.HealthProvider
import er.health.help.data.BASE_URL
import er.health.help.data.HealthService
import er.health.help.data.model.DiseasePrediction
import er.health.help.data.model.FoodPrediction
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun providesObjectMapper(): ObjectMapper {
        return ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    @Provides
    internal fun providesMappedClasses(): Array<Class<*>> {
        return arrayOf(DiseasePrediction::class.java, FoodPrediction::class.java)
    }

    @Provides
    @Singleton
    internal fun providesJacksonFactory(objectMapper: ObjectMapper): Converter.Factory {
        return JacksonConverterFactory.create(objectMapper)
    }

    @Provides
    @Singleton
    internal fun providesCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    @Singleton
    internal fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

    @Provides
    @Singleton
    internal fun stethoInterceptor(): StethoInterceptor {
        return StethoInterceptor()
    }

    @Provides
    @Singleton
    internal fun providesCache(): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MB

        return Cache(HealthProvider.context.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    internal fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, stethoInterceptor: StethoInterceptor, cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(stethoInterceptor)
                .cache(cache)
                .build()
    }

    @Provides
    @Singleton
    internal fun providesRetrofitBuilder(callAdapterFactory: CallAdapter.Factory, factory: Converter.Factory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(factory)
                .client(client)
                .baseUrl(BASE_URL)
                .build()
    }

    @Provides
    @Singleton
    internal fun providesEventService(retrofit: Retrofit): HealthService {
        return retrofit.create(HealthService::class.java)
    }

}