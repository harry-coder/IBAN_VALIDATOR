package com.example.ibanvalidator.dagger

import android.content.Context
import com.example.ibanvalidator.extentions.hasNetwork
import com.example.ibanvalidator.remote.NetworkResponseAdapterFactory
import com.example.ibanvalidator.requestinterface.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
 object AppModule {
    //Provider for repository
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(com.example.ibanvalidator.constants.Constants.BASE_URL_GITHUB)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    //Provider for Okhttp
    @Singleton
    @Provides
    fun provideOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor?,
        @ApplicationContext context: Context?,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()

            .addInterceptor(loggingInterceptor!!)
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (context?.hasNetwork() == true)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 1800).build()
                else
                    request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 30)
                        .build()
                chain.proceed(request)
            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun getCache(@ApplicationContext context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create<ApiInterface>(ApiInterface::class.java)
    }


}

