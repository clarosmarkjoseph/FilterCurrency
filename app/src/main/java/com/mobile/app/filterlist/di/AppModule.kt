package com.mobile.app.filterlist.di

import android.net.Uri
import android.util.Log
import com.mobile.app.filterlist.data.remote.CurrencyFilterService
import com.mobile.app.filterlist.data.repository.GetCurrencyFilterImpl
import com.mobile.app.filterlist.domain.repository.GetCurrencyFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val BASE_URI: Uri = Uri.Builder()
        .scheme("https")
        .encodedAuthority("wm0.mobimate.com")
        .build()

    @Singleton
    @Provides
    fun provideCurrencyFilterService(
        okHttpClient: OkHttpClient
    ): CurrencyFilterService {

        return Retrofit.Builder()
            .baseUrl(BASE_URI.toString())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CurrencyFilterService::class.java)
    }

    @Provides
    fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(logger = object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.d("okHttp:,", message)
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideAirlineRepository(currencyFilterService: CurrencyFilterService): GetCurrencyFilter {
        return GetCurrencyFilterImpl(
            currencyFilterService
        )
    }
}