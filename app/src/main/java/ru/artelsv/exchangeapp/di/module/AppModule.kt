package ru.artelsv.exchangeapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.artelsv.exchangeapp.data.ExchangeRepositoryImpl
import ru.artelsv.exchangeapp.data.database.ExchangeDatabase
import ru.artelsv.exchangeapp.data.datasource.ExchangeDataSource
import ru.artelsv.exchangeapp.data.datasource.impl.ExchangeLocalDataSource
import ru.artelsv.exchangeapp.data.datasource.impl.ExchangeRemoteDataSource
import ru.artelsv.exchangeapp.data.service.Constants.BASE_URL
import ru.artelsv.exchangeapp.data.service.ExchangeService
import ru.artelsv.exchangeapp.domain.ExchangeInteractor
import ru.artelsv.exchangeapp.domain.ExchangeInteractorImpl
import ru.artelsv.exchangeapp.domain.ExchangeRepository
import ru.artelsv.exchangeapp.utils.DefaultInterceptor
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient().newBuilder()
            .addInterceptor(DefaultInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
            ).build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ExchangeService =
        retrofit.create(ExchangeService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ExchangeDatabase {
        return Room.databaseBuilder(
            appContext,
            ExchangeDatabase::class.java,
            "exchangeDatabase1"
        ).build()
    }

    @Provides
    fun provideExchangeDao(db: ExchangeDatabase) = db.exchangeDao()
}

@Module
@InstallIn(SingletonComponent::class)
interface AppBindsModule {

    @Binds
    @Named("remote")
    fun bindRemoteDatasource(impl: ExchangeRemoteDataSource): ExchangeDataSource

    @Binds
    @Named("local")
    fun bindLocalDatasource(impl: ExchangeLocalDataSource): ExchangeDataSource

    @Binds
    fun bindExchangeRepository(impl: ExchangeRepositoryImpl): ExchangeRepository

    @Binds
    fun bindExchangeInteractor(impl: ExchangeInteractorImpl): ExchangeInteractor
}