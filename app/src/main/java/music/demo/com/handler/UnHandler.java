package music.demo.com.handler;

import android.app.Application;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.PrintWriter;

/**
 * 错误捕捉类
 * Created by liunian on 2018/1/10.
 */

public class UnHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CacheException";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Application application;

    public UnHandler(Application application){
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handlerException(ex) && mDefaultHandler!=null){
            mDefaultHandler.uncaughtException(thread,ex);
        } else {
            try{
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
            File file = new File(application.getCacheDir().getAbsolutePath() + "playlist");
            if (file.exists())file.delete();

        }
    }

    /**
     * 自定义错误处理，收集错误信息，发送错误报告等
     * @param ex
     * @return 如果处理就返回true ， 否则返回false
     */
    private boolean handlerException(final Throwable ex) {
        if (ex==null) return false;
        new  Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Log.e(ex.getMessage(),ex.getCause()+"");
//                Toast.makeText(application.getApplicationContext(), "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();
        File file = new File(application.getCacheDir().getAbsolutePath() + "/musicdemoCrash/");
        if (!file.exists()) file.mkdirs();
        try{
            PrintWriter printWriter = new PrintWriter(application.getCacheDir().getAbsolutePath() + "/musicdemoCrash/" + System.currentTimeMillis() + ".log");
            ex.printStackTrace(printWriter);
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return  true;
    }
}
