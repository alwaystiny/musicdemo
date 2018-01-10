package music.demo.com.util;

import android.content.Context;
import android.os.Handler;


/**
 * Created by liunian on 2018/1/10.
 */

public class HandlerUtil extends Handler {
    private static volatile  HandlerUtil instance;
    private HandlerUtil(){}

    public static HandlerUtil getInstance(){
        if (null ==instance){
            synchronized (HandlerUtil.class){
                if (null ==instance){
                    instance = new HandlerUtil();
                }
            }
        }
        return instance;
    }
}
