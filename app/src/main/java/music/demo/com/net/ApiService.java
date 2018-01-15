package music.demo.com.net;


import java.util.Map;

import io.reactivex.Observable;
import music.demo.com.LoodBean;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

/**
 *
 * Created by liunian on 2018/1/15.
 */

public interface ApiService {
    public static final String FORMATE = "json";
    public static final String URL_BASE = "http://tingapi.ting.baidu.com/";

    @Headers("User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
    @GET("v1/restserver/ting")
    Observable<LoodBean> getLoodView(@QueryMap Map<String,String>maps);
}
