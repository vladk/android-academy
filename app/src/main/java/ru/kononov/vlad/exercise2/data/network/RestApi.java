package ru.kononov.vlad.exercise2.data.network;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kononov.vlad.exercise2.data.network.endpoints.NewsEndpoint;

public final class RestApi {

    private static final String URL = "https://api.nytimes.com";
    private static final String API_KEY = "febc00f634c44f0481ac9daa766a703b";

    private static final int TIMEOUT_IN_SECONDS = 2;
    private static RestApi sRestApi;

    private final NewsEndpoint newsEndpoint;


    public static synchronized RestApi getInstance() {
        if (sRestApi == null) {
            sRestApi = new RestApi();
        }
        return sRestApi;
    }


    private RestApi() {
        final OkHttpClient httpClient = buildOkHttpClient();
        final Retrofit retrofit = buildRetrofitClient(httpClient);

        newsEndpoint = retrofit.create(NewsEndpoint.class);
    }

    @NonNull
    private Retrofit buildRetrofitClient(@NonNull OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @NonNull
    private OkHttpClient buildOkHttpClient() {
        final HttpLoggingInterceptor networkLogInterceptor = new HttpLoggingInterceptor();
        networkLogInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        return new OkHttpClient.Builder()
                .addInterceptor(ApiKeyInterceptor.create(API_KEY))
                .addInterceptor(networkLogInterceptor)
                .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public NewsEndpoint news() {
        return newsEndpoint;
    }
}
