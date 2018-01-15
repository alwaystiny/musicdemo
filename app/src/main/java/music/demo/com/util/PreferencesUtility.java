package music.demo.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 *
 * Created by liunian on 2018/1/15.
 */

public class PreferencesUtility {
    private static final String CURRENT_DATE ="currentdate" ;
    private static PreferencesUtility sInstance;
    private static SharedPreferences mPreferences;

    private PreferencesUtility(final Context context){
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static final PreferencesUtility getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesUtility(context.getApplicationContext());
        }
        return sInstance;
    }

    public boolean isCurrentDayFirst(String str){
        return mPreferences.getString(CURRENT_DATE, "").equals(str);
    }

    public void setCurrentDate(String str){
        final SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(CURRENT_DATE, str);
        editor.apply();
    }

    public String getItemPosition() {
        return mPreferences.getString("item_relative_position", "推荐歌单 最新专辑 主播电台");
    }
}
