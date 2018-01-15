package music.demo.com.widget;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import music.demo.com.LoodBean;
import music.demo.com.R;
import music.demo.com.util.HttpUtils;
import music.demo.com.util.NetworkUtils;
import okhttp3.ResponseBody;

/**
 *
 * Created by liunian on 2018/1/14.
 */

public class LoodView  extends FrameLayout{


    // 自动轮播时间间隔
    private static final long TIME_DELAY = 3;
    private Context mContext;
    private ArrayList<View> dotViewList;
    private ArrayList<ImageView> imageViewList;
    private int currentItem;
    private ViewPager viewPager;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;
    private ScheduledExecutorService scheduledExecutorService;
    private ArrayList<String> imageNet = new ArrayList<>();
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
    };
    private FPagerAdapter fPagerAdapter;

    public LoodView(Context context) {
        super(context);
        mContext = context;
    }

    public LoodView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        mContext = context;
    }

    public LoodView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        mContext = context;
        initImageView();
        initUI();
        if (isAutoPlay){
            startPlay();
        }

    }

    private void initImageView() {

        imageNet.add("res:/"+ R.mipmap.first);
        imageNet.add("res:/"+ R.mipmap.second);
        imageNet.add("res:/"+ R.mipmap.third);
        imageNet.add("res:/"+ R.mipmap.fourth);
        imageNet.add("res:/"+ R.mipmap.five);
        imageNet.add("res:/"+ R.mipmap.six);
        imageNet.add("res:/"+ R.mipmap.seven);


        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                HashMap<String, String> params = new HashMap<>();
                params.put("method","baidu.ting.plaza.getfocuspic");
                params.put("num","7");
                HttpUtils.getInstance(mContext)
                        .getRetrofit()
                        .getLoodView(params)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<LoodBean>() {
                            @Override
                            public void accept(LoodBean loodBean) throws Exception {
                                imageNet.clear();
                                List<LoodBean.PicBean> pic = loodBean.getPic();
                                for(int i =0;i<pic.size();i++){
                                    LoodBean.PicBean picBean = pic.get(i);
                                    Log.e("loodViewPic",picBean.getRandpic());
                                    imageNet.add(picBean.getRandpic());
                                }
                                imageViewList.clear();
                                initLoodViewData();
                                fPagerAdapter.notifyDataSetChanged();
                            }
                        });

                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                for(int i=0;i<7;i++){
                    imageViewList.get(i).setImageURI(Uri.parse(imageNet.get(i)));
                }
            }
        }.execute();



        // 存放轮播点下轮播点的view
        dotViewList = new ArrayList<>();

        // 存放轮播图片的list
        imageViewList = new ArrayList<>();

    }

    private void initUI() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.layout_loodview, this, true);
        initLoodViewData();

        dotViewList.add(findViewById(R.id.v_dot1));
        dotViewList.add(findViewById(R.id.v_dot2));
        dotViewList.add(findViewById(R.id.v_dot3));
        dotViewList.add(findViewById(R.id.v_dot4));
        dotViewList.add(findViewById(R.id.v_dot5));
        dotViewList.add(findViewById(R.id.v_dot6));
        dotViewList.add(findViewById(R.id.v_dot7));

        viewPager = (ViewPager) inflate.findViewById(R.id.loodview_vp);
        fPagerAdapter = new FPagerAdapter();
        viewPager.setAdapter(fPagerAdapter);
        viewPager.addOnPageChangeListener(new MyPagerChangeListener());
    }

    private void initLoodViewData() {
        for(String imagesId:imageNet){
            final SimpleDraweeView simpleDraweeView = new SimpleDraweeView(mContext);
            ControllerListener<ImageInfo> controllerListener = new BaseControllerListener<ImageInfo>(){

                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    if (imageInfo ==null) return;
                    QualityInfo qualityInfo = imageInfo.getQualityInfo();
                }

                @Override
                public void onFailure(String id, Throwable throwable) {
                    super.onFailure(id, throwable);
                    simpleDraweeView.setImageURI(Uri.parse("res:/"+ R.drawable.placeholder_disk_210));
                }

            };
            Uri uri = null;
            try{
                uri = Uri.parse(imagesId);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (uri!=null){
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri).build();
                AbstractDraweeController build = Fresco.newDraweeControllerBuilder()
                        .setOldController(simpleDraweeView.getController())
                        .setImageRequest(request)
                        .setControllerListener(controllerListener)
                        .build();
                simpleDraweeView.setController(build);
            }else{
                simpleDraweeView.setImageURI(Uri.parse("res:/"+R.drawable.placeholder_disk_210));
            }

            simpleDraweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(simpleDraweeView);

        }
    }

    class FPagerAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position));
            return imageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewList.get(position));
        }

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }
    }

    class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

        boolean isAutoPlay = false;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            for( int i =0;i<dotViewList.size();i++){
                if(i ==position){
                    dotViewList.get(i).setBackgroundResource(R.mipmap.red_point);
                } else {
                    dotViewList.get(i).setBackgroundResource(R.mipmap.grey_point);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state){
                // 滑动完毕
                case ViewPager.SCROLL_STATE_IDLE:
                    // 回到第一张图
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() -1 && !isAutoPlay){
                        viewPager.setCurrentItem(0);
                    } else if (viewPager.getCurrentItem() == 0 && !isAutoPlay){
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount()-1);
                    }
                    break;
                    // 手势滑动
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isAutoPlay = false;
                    stopPlay();
                    startPlay();
                    break;
                    // 界面切换中
                case ViewPager.SCROLL_STATE_SETTLING:
                    isAutoPlay = true;
                    break;
            }
        }
    }

    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new LoopTask(),1,TIME_DELAY,TimeUnit.SECONDS);
    }

    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    private class  LoopTask implements Runnable{

        @Override
        public void run() {
            synchronized (viewPager){
                currentItem = (currentItem+1)%imageViewList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    public void onDestory(){
        scheduledExecutorService.shutdown();
        scheduledExecutorService =null;
    }
    // 解决滑动冲突 ，在LoodView上时，将滑动事件交由本View解决
    // 解决到达最后一个item时向右继续滑动时不切换到第一个Item ，反而最外面的viewpager向右滑动
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
