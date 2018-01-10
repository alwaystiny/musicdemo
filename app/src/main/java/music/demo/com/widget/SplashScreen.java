package music.demo.com.widget;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import music.demo.com.R;

/**
 * 引导页图片 ，停留若干秒，然后自动消失
 * Created by liunian on 2018/1/10.
 */

public class SplashScreen {

    public final static int SLIDE_LEFT = 1;
    public final static int SLIDE_UP = 2;
    public final static int FADE_OUT = 3;
    private Activity activity;
    private Dialog splashDialog;

    public SplashScreen(Activity activity) {
        this.activity = activity;
    }

    /**
     *
     * @param imageResource 图片资源
     * @param animation 消失时的动画效果，取值可以是：SplashScreen.SLIDE_LEFT, SplashScreen.SLIDE_UP, SplashScreen.FADE
     */

    public void show(final int imageResource,final int animation){
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                DisplayMetrics displayMetrics = new DisplayMetrics();

                LinearLayout linearLayout = new LinearLayout(activity);
                linearLayout.setMinimumHeight(displayMetrics.heightPixels);
                linearLayout.setMinimumWidth(displayMetrics.widthPixels);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setBackgroundColor(Color.BLACK);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,0.0F));
                linearLayout.setBackgroundResource(imageResource);

                splashDialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
                if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    == WindowManager.LayoutParams.FLAG_FULLSCREEN){
                    splashDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }
                Window window = splashDialog.getWindow();
                switch (animation){
                    case SLIDE_LEFT:
                        window.setWindowAnimations(R.style.dialog_anim_slide_left);
                        break;
                    case SLIDE_UP:
                        window.setWindowAnimations(R.style.dialog_anim_slide_up);
                        break;
                    case FADE_OUT:
                        window.setWindowAnimations(R.style.dialog_anim_fade_out);
                        break;
                }
                splashDialog.setContentView(linearLayout);
                splashDialog.setCancelable(false);
                splashDialog.show();
            }
        };
        activity.runOnUiThread(runnable);
    }

    public void removeSplashScreen(){
        if (splashDialog != null && splashDialog.isShowing()) {
            splashDialog.dismiss();
            splashDialog = null;
        }
    }
}
