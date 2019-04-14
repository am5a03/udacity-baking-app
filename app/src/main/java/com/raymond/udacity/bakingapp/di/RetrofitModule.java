package com.raymond.udacity.bakingapp.di;


import com.raymond.udacity.bakingapp.api.ApiService;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class RetrofitModule {

    private static final String API = "https://api.themoviedb.org/3/";

    @Provides
    @Singleton
    @NonNull
    OkHttpClient.Builder provideOkHttp() {
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    @NonNull
    Retrofit provideRetrofit(OkHttpClient.Builder httpClient) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addNetworkInterceptor(chain -> {
            final Request request = chain.request();
            final HttpUrl url = request.url().newBuilder()
                    .build();
            return chain.proceed(request.newBuilder().url(url).build());
        });

        Retrofit.Builder retroBuilder = new Retrofit.Builder()
                .baseUrl(API)
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                ;

        return retroBuilder.build();
    }

    @Provides
    @Singleton
    @NonNull
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
