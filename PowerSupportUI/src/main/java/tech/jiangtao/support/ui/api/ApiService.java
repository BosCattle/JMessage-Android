package tech.jiangtao.support.ui.api;

import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import tech.jiangtao.support.kit.SupportIM;

/**
 * Class: ApiService </br>
 * Description: 服务器请求 </br>
 * Creator: kevin </br>
 * Email: jiangtao103cp@gmail.com </br>
 * Date: 29/12/2016 9:50 AM</br>
 * Update: 29/12/2016 9:50 AM </br>
 **/
public class ApiService {

  private volatile static ApiService INSTANCE;

  private OkHttpClient sOkHttpClient;
  private Retrofit sRetrofit;

  private ApiService(String baseUrl, Interceptor... interceptors) {

    sRetrofit = getRetrofit(baseUrl, interceptors);
  }

  public static ApiService getInstance() {
    if (INSTANCE == null) {
      synchronized (ApiService.class) {
        if (INSTANCE == null) {
          INSTANCE = new ApiService(SupportIM.API_ADDRESS,new HttpLoggingInterceptor());
        }
      }
    }
    return INSTANCE;
  }

  private OkHttpClient getOkHttpClient(Interceptor... interceptors) {
    if (sOkHttpClient == null) {
      synchronized (this) {
        if (sOkHttpClient == null) {
          OkHttpClient.Builder builder = new OkHttpClient.Builder();
          builder.connectTimeout(10, TimeUnit.SECONDS);
          builder.addInterceptor(new HttpLoggingInterceptor());
          /*for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
          }*/
          HttpLoggingInterceptor httpLoggingInterceptor =
              new HttpLoggingInterceptor();
          httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
          builder.addInterceptor(httpLoggingInterceptor);
          builder.retryOnConnectionFailure(true);
          sOkHttpClient = builder.build();
        }
      }
    }
    return sOkHttpClient;
  }

  private Retrofit getRetrofit(String baseUrl, Interceptor... interceptors) {
    if (sRetrofit == null) {
      synchronized (this) {
        if (sRetrofit == null) {
          sRetrofit = new Retrofit.Builder().client(getOkHttpClient(interceptors))
              .addConverterFactory(JacksonConverterFactory.create())
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .baseUrl(baseUrl)
              .build();
        }
      }
    }
    return sRetrofit;
  }

  public <T> T createApiService(Class<T> apiService) {
    return sRetrofit.create(apiService);
  }
}
