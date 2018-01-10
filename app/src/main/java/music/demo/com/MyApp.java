package music.demo.com;

import android.app.Application;
import android.content.Context;

import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import magicasakura.utils.ThemeUtils;
import music.demo.com.handler.UnHandler;
import music.demo.com.util.ThemeHelper;

/**
 *
 * Created by liunian on 2018/1/10.
 */

public class MyApp extends Application implements ThemeUtils.switchColor {
    private static int MAX_MEM = (int) Runtime.getRuntime().maxMemory() / 4;
    public static Context context;

    @Override
    public void onCreate() {
        // 初始化图片加载工具fresco
        Fresco.initialize(this, getImagePipeConfigure(this));
        super.onCreate();
        context = this;
        // bilibili 换肤
        ThemeUtils.setSwitchColor(this);
        // 奔溃捕捉类
        initCatchException();

    }

    private void initCatchException() {
        UnHandler catchExcep = new UnHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    private ImagePipelineConfig getImagePipeConfigure(Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEM,// 内存缓存中总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中图片的最大数量。
                MAX_MEM,// 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                Integer.MAX_VALUE,// 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE / 10);// 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {

            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };
        return ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setDownsampleEnabled(true)
                .build();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
    }



    @Override
    public int replaceColorById(Context context, int colorId) {
        if (ThemeHelper.isDefaultTheme(context)){
            return context.getResources().getColor(colorId);
        }
        String theme = getTheme(context);
        if (theme!=null){
            colorId = getThemeColorId(context,colorId,theme);
        }
        return context.getResources().getColor(colorId);
    }



    @Override
    public int replaceColor(Context context, int color) {
        if (ThemeHelper.isDefaultTheme(context))
            return color;
        String theme = getTheme(context);
        int colorId = -1;
        if (theme!=null){
            colorId = getThemeColor(context, color, theme);
        }
        return colorId!=-1?getResources().getColor(colorId):color;
    }

    private String getTheme(Context context) {
        if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_STORM) {
            return "blue";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_HOPE) {
            return "purple";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_WOOD) {
            return "green";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_LIGHT) {
            return "green_light";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_THUNDER) {
            return "yellow";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_SAND) {
            return "orange";
        } else if (ThemeHelper.getTheme(context) == ThemeHelper.CARD_FIREY) {
            return "red";
        }
        return null;
    }

    private int getThemeColorId(Context context, int colorId,String theme) {
        switch (colorId) {
            case R.color.theme_color_primary:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
            case R.color.theme_color_primary_dark:
                return context.getResources().getIdentifier(theme + "_dark", "color", getPackageName());
            case R.color.playbarProgressColor:
                return context.getResources().getIdentifier(theme + "_trans", "color", getPackageName());
        }
        return colorId;
    }

    private int getThemeColor(Context context, int color, String theme) {
        switch (color) {
            case 0xd20000:
                return context.getResources().getIdentifier(theme, "color", getPackageName());
        }
        return -1;
    }
}
