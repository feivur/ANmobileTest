package com.example.anmobiletest.api;

import com.example.anmobiletest.database.UserData;
import com.example.anmobiletest.user.User;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private final OkHttpClient.Builder httpClient;
    private final Retrofit.Builder builder;

    private NetworkService() {
        User user = UserData.getUser();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    if (isUserActive(user)){
                    Request request = chain.request().newBuilder()
                            .addHeader("User-Agent", "android")
                            .addHeader("Authorization", Credentials.basic(user.getLogin(), user.getPassword()))
                            .build();
                    return chain.proceed(request);}
                    else {
                        Request request = chain.request().newBuilder()
                                .addHeader("User-Agent", "android")
                                .build();
                        return chain.proceed(request);
                    }
                });

        RxJava3CallAdapterFactory rxAdapter = RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io());

        builder = new Retrofit.Builder()
                .baseUrl(user.getUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        httpClient.addInterceptor(logging)
                .build();

    }

    public static NetworkService getInstance() {
        return new NetworkService();
    }

    public <RESTService> RESTService createService(Class<RESTService> service) {
        Retrofit retrofit = builder.client(httpClient.connectTimeout(10, TimeUnit.MINUTES).readTimeout(10,TimeUnit.MINUTES).build()).build();
        return retrofit.create(service);
    }

    public static boolean isUserActive(User user)
    {
        return user.getStatus() != 2;
    }
}
