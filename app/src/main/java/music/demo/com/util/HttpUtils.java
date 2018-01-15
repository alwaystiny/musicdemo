package music.demo.com.util;

import android.content.Context;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.File;
import java.util.concurrent.TimeUnit;
import music.demo.com.net.ApiService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网路请求工具类
 * Created by liunian on 2018/1/14.
 */

public class HttpUtils {
    private volatile static HttpUtils instance;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private Context context;

    private HttpUtils(Context context){
        this.context = context.getApplicationContext();
        File sdcache = context.getCacheDir();
        Cache cache = new Cache(sdcache.getAbsoluteFile(), 1024 * 1024 * 30);
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this.context));
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .cookieJar(cookieJar)
                .cache(cache)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();


    }

    public static HttpUtils getInstance(Context context) {
        if (instance ==  null) {
            synchronized (HttpUtils.class){
                if (instance == null){
                    instance = new HttpUtils(context);
                }
            }
        }
        return instance;
    }

    public ApiService getRetrofit(){
        return  retrofit.create(ApiService.class);
    }
}
