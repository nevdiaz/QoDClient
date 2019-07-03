package com.nevdiaz.qodclient.service;

import com.nevdiaz.qodclient.BuildConfig;
import com.nevdiaz.qodclient.model.Quote;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface QodService {

  @GET("quotes/random")
  Single<Quote> random();

  static QodService getInstance(){
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final QodService INSTANCE;

    static{
      // Following five lines should be removed/commented out for production release.
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .client(client) //This should be removed/commented out for production release.
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create()) //TODO Check; maybe change?
          .build();
      INSTANCE = retrofit.create(QodService.class);
    }
  }


}
