package com.woowahan.ordering.di

import android.content.Context
import com.woowahan.ordering.data.remote.service.FoodService
import com.woowahan.ordering.util.hasNetwork
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun providesCache(@ApplicationContext context: Context): Cache {
        val size = (1024 * 1024 * 10).toLong()
        return Cache(context.cacheDir, size)
    }

    @Provides
    @Singleton
    fun providesInterceptor(@ApplicationContext context: Context) = Interceptor { chain ->
        var request = chain.request()
        request = if (context.hasNetwork())
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
            request.newBuilder()
                .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                .build()
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(cache: Cache, interceptor: Interceptor) = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl("https://api.codesquad.kr/onban/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun providesFoodService(retrofit: Retrofit) = retrofit.create<FoodService>()
}