package com.chicago311.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import com.chicago311.BuildConfig
import com.chicago311.ChicagoApplication
import com.chicago311.ViewModelFactory
import com.chicago311.data.local.ServiceRequestDatabase
import com.chicago311.data.local.ServicesDao
import com.chicago311.data.remote.ServiceRequestService
import com.chicago311.repository.ServiceRequestRepository
import com.chicago311.repository.ServiceRequestRepositoryImpl
import com.chicago311.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule(val app: ChicagoApplication) {

    private val BASE_URL = "http://test311api.cityofchicago.org/open311/v2/"

    @Provides
    @Singleton
    fun getApplicationContext(): Context {
        return app
    }

    @Provides
    @Singleton
    fun provideHttpLogging(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }

    @Provides
    @Singleton
    fun provideServiceRequestService(okHttpClient: OkHttpClient): ServiceRequestService {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .client(okHttpClient)
                .build()
                .create(ServiceRequestService::class.java)
    }

    @Provides
    @Singleton
    fun provideDb(context: Context): ServiceRequestDatabase {
        return Room.databaseBuilder(context, ServiceRequestDatabase::class.java, "service_requests.db").build()
    }

    @Provides
    @Singleton
    fun provideServicesDao(db: ServiceRequestDatabase): ServicesDao {
        return db.servicesDao()
    }

    @Provides
    @Singleton
    fun provideServiceRequestRepository(repository: ServiceRequestRepositoryImpl): ServiceRequestRepository {
        return repository
    }

    @Provides
    fun provideViewModelFactory(map: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return ViewModelFactory(map)
    }
}