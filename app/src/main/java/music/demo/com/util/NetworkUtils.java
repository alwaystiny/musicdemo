package music.demo.com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class NetworkUtils {
    private Context mContext = null;
    public NetworkUtils(Context pContext) {
        this.mContext = pContext;
    }

    public static final String IP_DEFAULT = "0.0.0.0";

    public static boolean isConnectInternet(final Context pContext) {
        final ConnectivityManager conManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }

        return false;
    }
}
