package com.example.anmobiletest.api;

import com.example.anmobiletest.HomeActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_LOGIN;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_PASSWORD;
import static com.example.anmobiletest.HomeActivity.APP_PREFERENCES_URL;

public class NetworkService {
    private static NetworkService mInstance = null;
    private final OkHttpClient.Builder httpClient;

    private final Retrofit.Builder builder;
    private String BASE_URL = "http://192.168.1.66:80/";
    private String login;
    private String pass;


    private NetworkService() {

        if(HomeActivity.mSettings.contains(APP_PREFERENCES_URL)){
           BASE_URL =HomeActivity.mSettings.getString(APP_PREFERENCES_URL,"error");
            login =HomeActivity.mSettings.getString(APP_PREFERENCES_LOGIN,"error");
            pass =HomeActivity.mSettings.getString(APP_PREFERENCES_PASSWORD,"error");

       }

        httpClient = new OkHttpClient.Builder()
                .authenticator((route, response) -> {
                    Request request = response.request();
                    if (request.header("Authorization")!=null)
                        return null;
                    return request.newBuilder()
                            .header("Authorization", Credentials.basic(login,pass))
                            .build();
                });

        RxJava3CallAdapterFactory rxAdapter = RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io());

        builder = new Retrofit.Builder()
                .baseUrl(BASE_URL+"/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter);

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            httpClient.addInterceptor(logging)
                    .build();

    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public <RESTService> RESTService createService(Class<RESTService> service) {
        Retrofit retrofit = builder.client(httpClient.connectTimeout(10, TimeUnit.MINUTES).build()).build();
        return retrofit.create(service);
    }
}
